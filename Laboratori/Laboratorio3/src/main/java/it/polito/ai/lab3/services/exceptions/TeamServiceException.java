package it.polito.ai.lab3.services.exceptions;

public class TeamServiceException extends RuntimeException {

    public TeamServiceException(String errorMessage) {
        super(errorMessage);
    }

}
