package br.com.lucas.virtualBankAPI.domain.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Getter
public class CreateUserRequest {

    private String name;
    private String email;
    private String password;

}
