package io.github.course.msclientes.application;

import io.github.course.msclientes.application.representation.ClienteDto;
import io.github.course.msclientes.domain.Cliente;
import io.github.course.msclientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor //Não necessita mais de Autowired. Criando um construtor do atributo já faz a injeção de dependencia
@Slf4j
public class ClientesResources {

    private final ClienteService service;

    @GetMapping
    public String status() {
        log.info("Obetendo o status do microservice de Clientes");
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteDto request){
        var cliente = request.toModel();
        service.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }
    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf) {
        var cliente = service.getByCpf(cpf);
        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }
}
