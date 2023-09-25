package br.com.lucas.virtualBankAPI.controllers.accounts;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionResponseDTO;
import br.com.lucas.virtualBankAPI.services.accounts.AccountServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("ap1/v1/account")
public class AccountController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AccountServices accountServices;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
        AccountDTO response = accountServices.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> findAll() {
        List<AccountDTO> response = accountServices.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDTO, @PathVariable Integer userId) {
        AccountDTO response = accountServices.create( modelMapper.map(accountDTO, Account.class), userId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> update(@RequestBody AccountDTO accountDTO,@PathVariable Long id) {
        AccountDTO accountUpdated = accountServices.update(modelMapper.map(accountDTO, Account.class), id);
        return  ResponseEntity.ok().body(accountUpdated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        accountServices.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/transaction/{id}")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(@PathVariable Long id) {
        List<TransactionResponseDTO> response = accountServices.getTransactions(id);
        return ResponseEntity.ok().body(response);
    }
}
