package com.ecommerce.model;

import java.util.List;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class Carrinho {
    
    private String id;
    private List<ItemPedido> itens;

    public void adicionarItem(ItemPedido item){

    }

    public void removerItem(String id){

    }

    public double calcularTotal(){
        return 20;
    }

    public double calcularTotalFrete(){
        return 10;
    }

    public void limpar(){

    }

    public boolean estaVazio(){
        return true;
    }

    public List<ItemPedido> getItens(){
        return itens;
    }
}
