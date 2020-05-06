package it.polito.ai.lab3.services.exceptions;

public class StudentNotFoundException extends TeamServiceException {

    public StudentNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
