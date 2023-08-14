package io.github.course.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.course.mscartoes.domain.Cartao;
import io.github.course.mscartoes.domain.ClienteCartao;
import io.github.course.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.github.course.mscartoes.repository.CartaoRepository;
import io.github.course.mscartoes.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receverSolicitacaoEmissao(@Payload String payload) {
        try {
            var mapper = new ObjectMapper();

            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);

            //toda vez que for fazer a Serialização ou Deserialização usa essa excessão
            //Aqui é somente fila e aqui está somente escutando
            //No avaliador era API e lá exibe a mensagem
        } catch (JsonProcessingException e) {
            log.error("Erro ao receber solicitação de emissao de cartao: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
