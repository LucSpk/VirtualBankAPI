package br.com.lucas.virtualBankAPI.services.userServices;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;

import java.util.List;

public interface UserServices {

    Usuario findById(Integer id);
    List<Usuario> findAll();
    UsuarioDTO create(UsuarioDTO usuarioDTO);
}
