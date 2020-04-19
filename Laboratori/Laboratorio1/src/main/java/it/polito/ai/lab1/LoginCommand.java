package it.polito.ai.lab1;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginCommand {

    @NotEmpty
    @Email
    String email;

    @NotEmpty
    @Size(min=4, max=50)
    String password;


}
