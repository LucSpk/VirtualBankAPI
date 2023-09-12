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

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody CreateUserRequest request) {
        UsuarioDTO userDTO = modelMapper.map(request, UsuarioDTO.class);
        UsuarioDTO userCreateDTO = userServices.create(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userCreateDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
