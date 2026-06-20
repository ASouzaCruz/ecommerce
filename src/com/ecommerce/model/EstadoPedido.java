package com.ecommerce.model;

public enum EstadoPedido {
    ABERTO,
    PAGO,
    SEPARANDO,
    ENVIADO,
    ENTREGUE,
    CANCELADO;

    public boolean podeTransicionarPara(EstadoPedido proximo) {
        switch (this) {
            case ABERTO:     return proximo == PAGO || proximo == CANCELADO;
            case PAGO:       return proximo == SEPARANDO || proximo == CANCELADO;
            case SEPARANDO:  return proximo == ENVIADO || proximo == CANCELADO;
            case ENVIADO:    return proximo == ENTREGUE;
            case ENTREGUE:   return false;
            case CANCELADO:  return false;
            default:         return false;
        }
    }
}
