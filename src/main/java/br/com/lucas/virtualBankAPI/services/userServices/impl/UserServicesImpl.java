package br.com.lucas.virtualBankAPI.services.userServices.impl;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import br.com.lucas.virtualBankAPI.services.userServices.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMappe;

    @Override
    public Usuario findById(Integer id) {
        Optional<Usuario> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(ErrorMessage.OBJETO_NAO_ENCONTRADO.getMessage()));
    }

    @Override
    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        Usuario user = modelMappe.map(usuarioDTO, Usuario.class);
        Usuario userCreated = userRepository.save(user);
        return modelMappe.map(userCreated, UsuarioDTO.class);
    }

}
