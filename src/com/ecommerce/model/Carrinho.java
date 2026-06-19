package com.ecommerce.model;

import java.util.List;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class Carrinho {
    
    private String id;
    private List<ItemPedido> itens;

    public Carrinho(String id, List<ItemPedido> itens) {
        this.id = id;
        this.itens = itens;
    }

    public void adicionarItem(ItemPedido item){
        itens.add(item);
    }

    public void removerItem(String id){
        itens.removeIf(item -> item.getId().equals(id));
    }
    
    public double calcularTotal(){
        return 20;
    }

    public double calcularTotalFrete(){
        return 10;
    }

    public void limpar(){

        if(estaVazio()){
            System.out.println("Carrinho ja esta vazio!");
            return;
        }

        itens.clear();

    }

    public boolean estaVazio(){
        return itens.isEmpty();
    }

    public List<ItemPedido> getItens(){
        return itens;
    }

    public String getId(){
        return id;
    }

}
