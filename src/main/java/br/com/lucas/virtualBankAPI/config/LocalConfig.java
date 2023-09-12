package br.com.lucas.virtualBankAPI.config;

import br.com.lucas.virtualBankAPI.domain.Usuario;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import br.com.lucas.virtualBankAPI.services.userServices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration @Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB() {
        Usuario user01 = new Usuario(null, "Lucas", "lucas@email.com", "123456");
        Usuario user02 = new Usuario(null, "Fulano", "fulano@email.com", "123456");

        repository.saveAll(List.of(user01, user02));
    }

}
