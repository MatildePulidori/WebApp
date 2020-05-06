package it.polito.ai.lab3.services.exceptions;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
