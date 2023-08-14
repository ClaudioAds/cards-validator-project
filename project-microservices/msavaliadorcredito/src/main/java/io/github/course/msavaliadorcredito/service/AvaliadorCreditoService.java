package io.github.course.msavaliadorcredito.service;

import feign.FeignException;
import io.github.course.msavaliadorcredito.controller.exception.DadosClienteNotFoundException;
import io.github.course.msavaliadorcredito.controller.exception.ErroComunicacaoMicroservicesException;
import io.github.course.msavaliadorcredito.controller.exception.ErroSolicitacaoCartaoExtepcion;
import io.github.course.msavaliadorcredito.domain.*;
import io.github.course.msavaliadorcredito.infra.CartaoResourceFeign;
import io.github.course.msavaliadorcredito.infra.ClienteResourceFeign;
import io.github.course.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceFeign clienteResourceFeign;
    private final CartaoResourceFeign cartoesResourceFeign;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    //objetivo é obter os dados do cliente (MSCLIENTES)
    //e os cartoes do cliente (MSCARTOES)
    public SituacaoCliente obeterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeign.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceFeign.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartaoClientes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeign.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceFeign.getCartoesRendaAteh(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());  //renda em BigDecimal
                var fator = idadeBD.divide(BigDecimal.valueOf(10)); //dividindo a idade por 10
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);//multiplicando o valor pelo limite

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try {
            emissaoCartaoPublisher.solicitarCartao(dados);
            //criando um numero de Protocolo que seria usado para o atendimento no mundo real
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoExtepcion(e.getMessage());
        }
    }
}
