package io.github.course.msclientes.application.representation;

import io.github.course.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteDto {

    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel() {
        return new Cliente(cpf, nome, idade);
    }
}
