package it.polito.ai.lab2.services;

public class TeamServiceException extends RuntimeException {
    @Override
    public String toString() {
        return "Exception: Student Not Found";
    }

}
