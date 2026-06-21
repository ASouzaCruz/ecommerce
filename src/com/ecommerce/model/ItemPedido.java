package com.ecommerce.model;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

import com.ecommerce.model.produto.Produto;

public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double precoUnitario; // Regra: preco congelado no momento da compra

    public ItemPedido(Produto produto, int quantidade) {
        if (produto == null) throw new IllegalArgumentException("Produto nao pode ser nulo.");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        if (!produto.estaDisponivel()) throw new IllegalStateException("Produto '" + produto.getNome() + "' nao esta disponivel.");
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco(); // Congelado no momento da criacao
    }
    //construtor pra persistencia
    public ItemPedido(Produto produto, int quantidade, double precoUnitarioHistorico) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitarioHistorico;
    }

    public double calcularSubtotal() {
        return precoUnitario * quantidade;
    }

    public double calcularFrete() {
        return produto.calcularFrete() * quantidade;
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoUnitario() { return precoUnitario; }
    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        this.quantidade = quantidade;
    }
    public void setPrecoUnitario(double preco) { this.precoUnitario = preco; }

    @Override
    public String toString() {
        return String.format("%s x%d | Unit: R$ %.2f | Subtotal: R$ %.2f",
                produto.getNome(), quantidade, precoUnitario, calcularSubtotal());
    }
}