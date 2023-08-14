package io.github.course.mscartoes.service;

import io.github.course.mscartoes.domain.Cartao;
import io.github.course.mscartoes.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository repository;

    @Transactional
    public Cartao save(Cartao cartao) {
        return repository.save((cartao));
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda){
        var rendaBigdecimal = BigDecimal.valueOf(renda);
        return repository.findByRendaLessThanEqual(rendaBigdecimal);
    }
}
