package com.ecommerce.model.produto;

import java.util.Random;
import java.util.UUID;

public abstract class Produto {
    private String id;
    private String nome;
    private String descricao;
    private double preco;
    private boolean ativo;

    public Produto(String nome, String descricao, double preco) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome do produto nao pode ser vazio.");
        if (preco < 0) throw new IllegalArgumentException("Preco nao pode ser negativo.");
        Random random = new Random();
        this.id = "PROD" +  random.nextInt(10000);
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.ativo = true;
    }

    // Polimorfismo 1 - comportamento de negocio diferente por tipo
    public abstract double calcularFrete();
    public abstract boolean estaDisponivel();
    public abstract String getTipo();

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public boolean isAtivo() { return ativo; }
    public void setId(String id) { this.id = id; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome nao pode ser vazio.");
        this.nome = nome;
    }
    public void setPreco(double preco) {
        if (preco < 0) throw new IllegalArgumentException("Preco nao pode ser negativo.");
        this.preco = preco;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f | %s", getTipo(), nome, preco,
                estaDisponivel() ? "Disponivel" : "Indisponivel");
    }
}
