package br.com.lucas.virtualBankAPI.services.userServices;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;

import java.util.List;

public interface UserServices {

    Usuario findById(Integer id);
    List<Usuario> findAll();
}
