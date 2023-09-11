package br.com.lucas.virtualBankAPI.services.userServices.impl;

import br.com.lucas.virtualBankAPI.domain.Usuario;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import br.com.lucas.virtualBankAPI.services.userServices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Usuario findById(Integer id) {
        Optional<Usuario> user = userRepository.findById(id);
        return user.orElse(null);
    }
}
