package io.github.course.eurekaserver.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf() //não ter problema com formulário
                .disable()
                .authorizeHttpRequests() //autorizando as requisições
                .anyRequest().authenticated() //toda requisição que chegar tem que estar autenticado com o usuário e senha
                .and() //tod microserviço de fora que for se registrar no eureka tem que ser autenticado
                .httpBasic();
    }
}
