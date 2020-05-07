package it.polito.ai.lab3.services.exceptions;

public class CourseNotEnabledException extends TeamServiceException {

    public CourseNotEnabledException(String errorMessage) {

        super(errorMessage);
    }
}
