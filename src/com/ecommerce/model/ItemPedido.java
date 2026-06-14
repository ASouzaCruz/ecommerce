package com.ecommerce.model;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class ItemPedido {
    
    private String id;
    private int quantidade;
    private double precoUnitario;
    private double freteItem;

    public ItemPedido(String id, int quantidade, double precoUnitario, double freteItem) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.freteItem = freteItem;
    }

    public double calcularSubtotal(){
        return 10;
    }

    public double calcularTotalComFrete(){
        return 10;
    }

    public String getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }
    
}
