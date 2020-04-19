package it.polito.ai.lab1;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Builder
@Value
public class RegistrationDetails {

    String nome;
    String cognome;
    String email;
    String password;
    Date registrationDate;
}
