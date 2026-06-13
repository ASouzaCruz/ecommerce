package com.ecommerce.model;

public class Endereco {
    private String id;
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(String id, String rua, String numero, String complemento, String cidade, String estado, String cep){

        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getId(){
        return id;
    }

    @Override
    public String toString(){
        return rua + ", " + numero + 
        (complemento.isEmpty() ? "" : " - " + complemento) + 
        " - " + cidade + "/" + estado + " CEP:" + cep;
    }
}
