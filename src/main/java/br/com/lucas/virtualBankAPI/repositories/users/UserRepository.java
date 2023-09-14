package br.com.lucas.virtualBankAPI.repositories.users;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

}
