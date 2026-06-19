package com.ecommerce.model.produto;

public abstract class Produto {
    
    private String id;
    private String nome;
    private String descricao;
    private double preco;
    private boolean ativo;

    public Produto(String id, String nome, String descricao, double preco){

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.ativo = true;
        
    }


    public abstract boolean estaDisponivel();

    public abstract String getTipo();

    public double getPreco(){
        return preco;
    }
    
    public String getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }
}
