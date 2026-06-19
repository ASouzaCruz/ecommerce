package com.ecommerce.model.produto;

public class ProdutoFisico extends Produto {
    private double pesoKg;
    private int estoque;

    // talvez esse atributo devese estar em outro local, como em carrinho ou pedido
    private static final double TARIFA_KG = 5.0;


    public ProdutoFisico(String id, String nome, String descricao, double preco, double pesoKg, int estoque){

        super(id,nome,descricao,preco);

        this.pesoKg = pesoKg;

        // talvez seja meelhor trocar estoque por outra variavel, nao faz tanto sentido ela esta atrelada totalmente a produto fisico
        this.estoque = estoque;
    }

    @Override
    public boolean estaDisponivel(){

        return estoque > 0;

    }


    @Override
    public String getTipo(){

        return "Produto Físico";

    }

    //funcoes adicionadas depois

    public double getPesoKg() {
        return pesoKg;
    }
}
