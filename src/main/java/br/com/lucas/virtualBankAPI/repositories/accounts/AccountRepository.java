package br.com.lucas.virtualBankAPI.repositories.accounts;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccNumber(String accNumber);
}
