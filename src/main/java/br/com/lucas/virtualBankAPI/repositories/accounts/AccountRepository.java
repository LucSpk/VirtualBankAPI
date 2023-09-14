package br.com.lucas.virtualBankAPI.repositories.accounts;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
