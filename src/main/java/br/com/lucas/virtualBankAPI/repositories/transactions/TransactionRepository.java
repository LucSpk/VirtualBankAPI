package br.com.lucas.virtualBankAPI.repositories.transactions;

import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
