package it.polito.ai.lab2.services;

public class CourseNotFoundException extends TeamServiceException {
    @Override
    public String toString() {
        return "Exception: Course Not Found";
    }
}
