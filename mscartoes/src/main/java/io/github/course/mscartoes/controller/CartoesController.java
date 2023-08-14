package io.github.course.mscartoes.controller;

import io.github.course.mscartoes.domain.Cartao;
import io.github.course.mscartoes.domain.ClienteCartao;
import io.github.course.mscartoes.dto.CartaoDTO;
import io.github.course.mscartoes.dto.CartoesClienteDTO;
import io.github.course.mscartoes.service.CartaoService;
import io.github.course.mscartoes.service.ClientecartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("cartoes")
public class CartoesController {

    private final CartaoService cartaoService;
    private final ClientecartaoService clientecartaoService;

    @GetMapping
    public String status() {
        return "ok";
    }
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody CartaoDTO request){
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> rendaCartoesAteh(@RequestParam("renda") Long renda) {
        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesClienteDTO>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<ClienteCartao> lista = clientecartaoService.listCartoesByCpf(cpf);
        List<CartoesClienteDTO> resultList = lista.stream()
                .map(CartoesClienteDTO::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }
}
