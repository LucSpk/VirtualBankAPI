package br.com.lucas.virtualBankAPI.domain.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class CreateUserRequest {

    private String name;
    private String email;
    private String password;

}
