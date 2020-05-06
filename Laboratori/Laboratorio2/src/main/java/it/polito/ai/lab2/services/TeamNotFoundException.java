package it.polito.ai.lab2.services;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
