package io.github.course.mscartoes.service;

import io.github.course.mscartoes.domain.ClienteCartao;
import io.github.course.mscartoes.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientecartaoService {

    private final ClienteCartaoRepository clienteCartaoRepository;

    public List<ClienteCartao> listCartoesByCpf(String cpf){
        return clienteCartaoRepository.findByCpf(cpf);
    }

}
