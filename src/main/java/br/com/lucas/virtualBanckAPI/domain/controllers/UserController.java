package br.com.lucas.virtualBanckAPI.domain.controllers;

import br.com.lucas.virtualBanckAPI.domain.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("ap1/v1/user")
public class UserController {

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(new Usuario(1, "Lucas Alves", "lucas@email.com", "123456"));
    }
}
