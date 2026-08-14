package com.example.clientes.exception;

public class ClienteNotFoundException extends RuntimeException{

	public ClienteNotFoundException(String message) {
        super(message);
    }
}
