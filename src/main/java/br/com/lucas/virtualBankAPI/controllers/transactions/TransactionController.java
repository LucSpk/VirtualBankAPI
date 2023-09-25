package br.com.lucas.virtualBankAPI.controllers.transactions;

import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionRequest;
import br.com.lucas.virtualBankAPI.services.transactions.TransactionServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("ap1/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionServices transactionServices;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable Long id) {
        TransactionDTO response = transactionServices.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> findAll() {
        List<TransactionDTO> response = transactionServices.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> transaction(@RequestBody TransactionRequest request) {
        transactionServices.transaction(request);
        return ResponseEntity.ok().build();
    }

}
