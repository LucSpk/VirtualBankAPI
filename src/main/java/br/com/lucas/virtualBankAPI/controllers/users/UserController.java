package br.com.lucas.virtualBankAPI.controllers.users;

import br.com.lucas.virtualBankAPI.domain.Usuario;
import br.com.lucas.virtualBankAPI.services.userServices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("ap1/v1/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userServices.findById(id));
    }
}
