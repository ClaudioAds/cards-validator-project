package io.github.course.mscartoes.dto;

import io.github.course.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesClienteDTO {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesClienteDTO fromModel(ClienteCartao model){
        return new CartoesClienteDTO(
                model.getCartao().getNome(),
                model.getCartao().getBandeira().toString(),
                model.getLimite()
        );
    }
}
