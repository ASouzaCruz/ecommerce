package com.ecommerce.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(String identificador) {
        super("Produto nao encontrado: " + identificador);
    }
}
