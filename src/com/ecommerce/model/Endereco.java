package com.ecommerce.model;

public class Endereco {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(String rua, String numero, String bairro, String cidade, String estado, String cep) {
        if (cep == null || cep.replaceAll("[^0-9]","").length() != 8)
            throw new IllegalArgumentException("CEP invalido. Deve conter 8 digitos.");
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep.replaceAll("[^0-9]","");
    }

    public String getRua() { return rua; }
    public String getNumero() { return numero; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getCep() { return cep; }
    public void setRua(String rua) { this.rua = rua; }
    public void setNumero(String numero) { this.numero = numero; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setBairro(String bairro) {this.bairro = bairro;}
    public void setCep(String cep) {this.cep = cep;}

    @Override
    public String toString() {
        return String.format("%s, %s - %s, %s/%s - CEP: %s", rua, numero, bairro, cidade, estado, cep);
    }

}
