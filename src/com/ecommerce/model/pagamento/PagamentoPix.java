package com.ecommerce.model.pagamento;

public class PagamentoPix extends Pagamento {
    private String chavePix;
    private String codigoPix;

    public PagamentoPix(double valor) {
        super(valor);
        this.chavePix = "eccomerce@loja.com";
        this.codigoPix = gerarCodigoPix();
    }

    @Override
    public void processar() {
        System.out.println("  Gerando QR Code Pix...");
        System.out.println("  Chave: " + chavePix);
        System.out.println("  Codigo: " + codigoPix);
        marcarComoProcessado();
        System.out.println("  Pagamento Pix confirmado!");
    }

    @Override
    public String getDescricao() { return "Pix (" + chavePix + ")"; }

    private String gerarCodigoPix() {
        return "PIX" + System.currentTimeMillis();
    }

    public String getChavePix() { return chavePix; }
    public String getCodigoPix() { return codigoPix; }
    public void setChavePix(String c) { this.chavePix = c; }
    public void setCodigoPix(String c) { this.codigoPix = c; }
}