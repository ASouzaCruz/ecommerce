package com.ecommerce.exception;

public class CarrinhoVazioException extends RuntimeException {
    public CarrinhoVazioException() {
        super("Nao e possivel finalizar um pedido com carrinho vazio.");
    }
}