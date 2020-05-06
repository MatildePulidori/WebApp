package it.polito.ai.lab3.services.exceptions;

import it.polito.ai.lab3.services.exceptions.TeamServiceException;

public class CourseNotFoundException extends TeamServiceException {

    public CourseNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
