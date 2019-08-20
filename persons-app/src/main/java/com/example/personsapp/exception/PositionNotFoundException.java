package com.example.personsapp.exception;

public class PositionNotFoundException extends RuntimeException {

    public PositionNotFoundException(String positionId) {
        super("Position not found with id " + positionId);
    }
}
