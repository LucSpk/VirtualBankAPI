package br.com.lucas.virtualBankAPI.controllers.users;

import br.com.lucas.virtualBankAPI.domain.users.CreateUserRequest;
import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import br.com.lucas.virtualBankAPI.services.userServices.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        UsuarioDTO response = userServices.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> response = userServices.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody CreateUserRequest request) {
        UsuarioDTO userCreateDTO = userServices.create(modelMapper.map(request, Usuario.class));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userCreateDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
