package br.com.lucas.virtualBankAPI.services.userServices;

import br.com.lucas.virtualBankAPI.domain.Usuario;

public interface UserServices {

    Usuario findById(Integer id);
}
