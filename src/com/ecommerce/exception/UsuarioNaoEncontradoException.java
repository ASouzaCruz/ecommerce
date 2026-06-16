package com.ecommerce.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String identificador) {
        super("Usuario nao encontrado: " + identificador);
    }
}

