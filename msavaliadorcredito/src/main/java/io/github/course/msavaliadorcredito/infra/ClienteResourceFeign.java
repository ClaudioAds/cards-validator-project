package io.github.course.msavaliadorcredito.infra;

import io.github.course.msavaliadorcredito.domain.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//o value vai pegar o valor no nome do microservico que está no arquivo Yml em String>Application>Name
@FeignClient(value = "msclientes", path = "/clientes")
public interface ClienteResourceFeign {
    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);


}
