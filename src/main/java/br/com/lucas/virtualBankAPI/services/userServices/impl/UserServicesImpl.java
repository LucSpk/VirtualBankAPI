package br.com.lucas.virtualBankAPI.services.userServices.impl;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import br.com.lucas.virtualBankAPI.services.exceptions.DataIntegrityViolationException;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import br.com.lucas.virtualBankAPI.services.userServices.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMappe;

    @Override
    public UsuarioDTO findById(Integer id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ErrorMessage.OBJETO_NAO_ENCONTRADO.getMessage()));
        return modelMappe.map(user, UsuarioDTO.class);
    }

    @Override
    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarioList = userRepository.findAll();
        return usuarioList.stream().map((user) -> modelMappe.map(user, UsuarioDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO create(Usuario usuario) {
        this.emailIsPresent(usuario);
        Usuario userCreated = userRepository.save(usuario);
        return modelMappe.map(userCreated, UsuarioDTO.class);
    }

    public void emailIsPresent(Usuario usuario) {
        Optional<Usuario> response = userRepository.findByEmail(usuario.getEmail());
        if(response.isPresent())
            throw new DataIntegrityViolationException(ErrorMessage.EAMIL_JA_CADASTRADO.getMessage());
    }
}
