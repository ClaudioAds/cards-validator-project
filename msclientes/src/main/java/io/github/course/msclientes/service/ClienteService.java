package io.github.course.msclientes.service;

import io.github.course.msclientes.domain.Cliente;
import io.github.course.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    //pq é uma dependencia obrigatória e tem que iniciar ele no construtor
    private final ClienteRepository repository;
    @Transactional
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    public Optional<Cliente> getByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
