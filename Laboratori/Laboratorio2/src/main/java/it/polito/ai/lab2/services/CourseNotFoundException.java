package it.polito.ai.lab2.services;

public class CourseNotFoundException extends TeamServiceException {
    
    public CourseNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
