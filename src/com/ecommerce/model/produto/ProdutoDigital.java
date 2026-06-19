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
    public boolean estaDisponivel(){

        return true;

    }


    @Override
    public String getTipo(){

        return "Produto Digital";

    }

    //funcoes adicionadas depois

    public String getLinkDownload() {
        return linkDownload;
    }


    public String getFormato() {
        return formato;
    }
}
