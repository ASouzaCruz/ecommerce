package com.ecommerce.exception;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(String produto, int disponivel, int solicitado) {
        super(String.format("Estoque insuficiente para '%s'. Disponivel: %d, Solicitado: %d",
                produto, disponivel, solicitado));
    }
}
