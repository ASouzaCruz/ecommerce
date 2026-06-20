package com.ecommerce.model.pagamento;

// Implementação inicial baseada no diagrama UML. Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class PagamentoPix extends Pagamento {
    private String chavePix;
    private String codigoPix;

    public PagamentoPix(double valor, String chavePix) {
        super(valor);
        if (chavePix == null || chavePix.isBlank())
            throw new IllegalArgumentException("Chave Pix invalida.");
        this.chavePix = chavePix;
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