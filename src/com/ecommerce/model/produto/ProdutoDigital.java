package com.ecommerce.model.produto;

public class ProdutoDigital extends Produto {
    private String linkDownload;
    private String formato;

    public ProdutoDigital(String nome, String descricao, double preco, String linkDownload, String formato) {
        super(nome, descricao, preco);
        if (linkDownload == null || linkDownload.isBlank())
            throw new IllegalArgumentException("Link de download nao pode ser vazio.");
        this.linkDownload = linkDownload;
        this.formato = formato;
    }

    @Override
    public double calcularFrete() { return 0.0; } // Digital nao tem frete

    @Override
    public boolean estaDisponivel() { return isAtivo(); } // Sem limite de estoque

    @Override
    public String getTipo() { return "DIGITAL"; }

    public String getLinkDownload() { return linkDownload; }
    public String getFormato() { return formato; }
    public void setLinkDownload(String link) { this.linkDownload = link; }
    public void setFormato(String formato) { this.formato = formato; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Formato: %s | Frete: Gratis", formato);
    }
}
