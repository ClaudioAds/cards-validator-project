package io.github.course.msavaliadorcredito.infra;

import io.github.course.msavaliadorcredito.domain.Cartao;
import io.github.course.msavaliadorcredito.domain.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//o value vai pegar o valor no nome do microservico que está no arquivo Yml em String>Application>Name
@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartaoResourceFeign {
    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

    @GetMapping(params = "renda")
    ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda);


}
