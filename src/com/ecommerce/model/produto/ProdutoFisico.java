package com.ecommerce.model.produto;

public class ProdutoFisico extends Produto {
    private double pesoKg;
    private int estoque;

    private static final double TARIFA_KG = 5.0;


    public ProdutoFisico(String id, String nome, String descricao, double preco, double pesoKg, int estoque){

        super(id,nome,descricao,preco);

        this.pesoKg = pesoKg;
        this.estoque = estoque;
    }


    @Override
    public double calcularFrete(){

        return pesoKg * TARIFA_KG;

    }


    @Override
    public boolean estaDisponivel(){

        return estoque > 0;

    }


    @Override
    public String getTipo(){

        return "Produto Físico";

    }
}
