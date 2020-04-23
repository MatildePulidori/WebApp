package it.polito.ai.lab2.services;

public class StudentNotFoundException extends TeamServiceException {

    public StudentNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
