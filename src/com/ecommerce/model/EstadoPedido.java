package com.ecommerce.model;

public enum EstadoPedido {

    ABERTO,
    PAGO,
    SEPARANDO,
    ENVIADO,
    ENTREGUE,
    CANCELADO;

// Verifica se o pedido pode mudar para o estado informado

    public boolean podeTransicionarPara (EstadoPedido estadoPedido){
        
        if(this == ABERTO && estadoPedido == PAGO){
            return true;
        }
        else if(this == PAGO && estadoPedido == SEPARANDO){
            return true;
        }
        else if(this == SEPARANDO && estadoPedido == ENVIADO){
            return true;
        }
        else if(this == ENVIADO && estadoPedido == ENTREGUE){
            return true;
        }
        else if((this == ABERTO || this == PAGO || this == SEPARANDO) && estadoPedido == CANCELADO){
            return true;
        }

        return false;

    }

}
