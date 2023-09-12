package br.com.lucas.virtualBankAPI.controllers.users;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.dto.UsuarioDTO;
import br.com.lucas.virtualBankAPI.services.userServices.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequestMapping("ap1/v1/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserServices userServices;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(modelMapper.map(userServices.findById(id), UsuarioDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok().body(
                userServices.findAll()
                        .stream()
                        .map(
                                (user) -> modelMapper.map(user, UsuarioDTO.class)
                        ).collect(Collectors.toList())
        );
    }
}
