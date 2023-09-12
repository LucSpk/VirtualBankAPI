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
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        Usuario user = modelMappe.map(usuarioDTO, Usuario.class);
        Usuario userCreated = userRepository.save(user);
        return modelMappe.map(userCreated, UsuarioDTO.class);
    }

}
