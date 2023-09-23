package br.com.lucas.virtualBankAPI.config;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionRequest;
import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.enums.transactions.TransactionType;
import br.com.lucas.virtualBankAPI.repositories.accounts.AccountRepository;
import br.com.lucas.virtualBankAPI.repositories.transactions.TransactionRepository;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import br.com.lucas.virtualBankAPI.services.transactions.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.List;

@Configuration @Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionServices transactionServices;

    @Bean
    public void startDB() {
//        Usuario user01 = new Usuario(null, "Lucas", "lucas@email.com", "123456");
//        Usuario user02 = new Usuario(null, "Fulano", "fulano@email.com", "123456");
//
//        userRepository.saveAll(List.of(user01, user02));
//
//        Account account01 = new Account(null, "123456789", 100.0, user01);
//        Account account02 = new Account(null, "535353535", 300.0, user02);
//
//        accountRepository.saveAll(List.of(account01, account02));
//
//        TransactionRequest request01 = new TransactionRequest(TransactionType.DEPOSIT, null, account01.getId(), 200.0);
//        this.transactionServices.transaction(request01);
//        TransactionRequest request02 = new TransactionRequest(TransactionType.DEPOSIT, null, account02.getId(), 375.0);
//        this.transactionServices.transaction(request02);
//        TransactionRequest request03 = new TransactionRequest(TransactionType.TRANSFER, account02.getId(), account01.getId(), 25.0);
//        this.transactionServices.transaction(request03);
//        TransactionRequest request04 = new TransactionRequest(TransactionType.PAYMENT, account01.getId(), account02.getId(), 50.0);
//        this.transactionServices.transaction(request04);
//        TransactionRequest request05 = new TransactionRequest(TransactionType.WITHDRAW, account01.getId(), null, 15.0);
//        this.transactionServices.transaction(request05);

    }

}
