package br.com.lucas.virtualBankAPI.repositories.users;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Integer> {

}
