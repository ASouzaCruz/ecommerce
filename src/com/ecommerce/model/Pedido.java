package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class Pedido {

    private String id;
    private EstadoPedido estado;
    private LocalDateTime dataCriacao, dataAtualizacao;
    private List<ItemPedido> itens;

    public void avancarEstado(EstadoPedido estado){

    }

    public void registrarPagamento(){

    }

    public void cancelar(){

    }

    public double calcularTotal(){
        return 10;
    }

    public double calcularTotalFrete(){
        return 20;
    }

    public String getId(){
        return id;
    } 

    public EstadoPedido getEstado(){
        return estado;
    }
}
