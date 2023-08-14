package io.github.course.msavaliadorcredito.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RetornoAvaliacaoCliente {
    private List<CartaoAprovado> catoesAprovados;
}
