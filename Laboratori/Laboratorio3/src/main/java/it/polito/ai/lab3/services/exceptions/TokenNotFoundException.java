package it.polito.ai.lab3.services.exceptions;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
