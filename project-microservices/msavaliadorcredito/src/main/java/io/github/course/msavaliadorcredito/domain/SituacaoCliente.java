package io.github.course.msavaliadorcredito.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SituacaoCliente {
    private DadosCliente cliente;
    private List<CartaoCliente> cartaoClientes;
}
