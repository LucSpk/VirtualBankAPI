package br.com.lucas.virtualBankAPI.config;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.repositories.accounts.AccountRepository;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration @Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Bean
    public void startDB() {
        Usuario user01 = new Usuario(null, "Lucas", "lucas@email.com", "123456");
        Usuario user02 = new Usuario(null, "Fulano", "fulano@email.com", "123456");

        userRepository.saveAll(List.of(user01, user02));

        Account account01 = new Account(null, "123456789", 100.0, user01);
        Account account02 = new Account(null, "535353535", 300.0, user02);

        accountRepository.saveAll(List.of(account01, account02));

    }

}
