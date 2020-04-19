package it.polito.ai.lab1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegistrationCommand {

     @NotEmpty(message = "{nomeVuoto}")
     String nome;

     @NotEmpty(message = "{cognomeVuoto}")
     String cognome;

     @Email(message = "{emailMessage}")
     String email;


     @Size(min=9, max=50, message = "{passwordMessage}")
     String password;

     String passwordValidation;


     boolean privacyCheck;

     public boolean validatePassword(){
          if (password.equals(passwordValidation)) return true;
          else return false;
     }


}
