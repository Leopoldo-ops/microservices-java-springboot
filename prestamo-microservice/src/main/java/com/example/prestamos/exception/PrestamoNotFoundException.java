package com.example.prestamos.exception;

public class PrestamoNotFoundException extends RuntimeException {

    public PrestamoNotFoundException(String message) {
        super(message);
    }
}
