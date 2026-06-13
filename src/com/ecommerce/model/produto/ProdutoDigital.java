package com.ecommerce.model.produto;

public class ProdutoDigital extends Produto {
    private String linkDownload;
    private String formato;


    public ProdutoDigital(String id, String nome, String descricao, double preco, String linkDownload, String formato){

        super(id,nome,descricao,preco);

        this.linkDownload = linkDownload;
        this.formato = formato;
    }


    @Override
    public double calcularFrete(){

        return 0;

    }


    @Override
    public boolean estaDisponivel(){

        return true;

    }


    @Override
    public String getTipo(){

        return "Produto Digital";

    }
}
