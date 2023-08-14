package io.github.course.msavaliadorcredito.domain;

import lombok.Data;

@Data
public class DadosSolicitacaoEmissaoCartao {
    private Long idCartao;
    private String cpf;
    private String endereco;
    private String limiteLiberado;
}
