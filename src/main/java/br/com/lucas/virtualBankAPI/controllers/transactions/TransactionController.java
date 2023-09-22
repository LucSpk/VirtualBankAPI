package br.com.lucas.virtualBankAPI.controllers.transactions;

import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionRequest;
import br.com.lucas.virtualBankAPI.services.transactions.TransactionServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("ap1/v1/transaction")
public class TransactionController {

    @Autowired
    private ModelMapper modelMapper;
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

    @PostMapping(value = "/{sourceAccountId}/{destinationAccount}")
    public ResponseEntity<TransactionDTO> create(
            @RequestBody TransactionDTO request,
            @PathVariable Long sourceAccountId,
            @PathVariable Long destinationAccount
    ) {
        TransactionDTO response = transactionServices.create(modelMapper.map(request, Transaction.class), sourceAccountId, destinationAccount);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TransactionDTO> update(@RequestBody TransactionDTO request, @PathVariable Long id) {
        TransactionDTO response = transactionServices.update(modelMapper.map(request, Transaction.class), id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> transaction(@RequestBody TransactionRequest request) {
        transactionServices.transaction(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        transactionServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
