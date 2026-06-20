package com.ecommerce.model.pagamento;

public class PagamentoCartao extends Pagamento {
    private String numeroCartao;
    private int parcelas;
    private String nomeTitular;

    public PagamentoCartao(double valor, String numeroCartao, int parcelas, String nomeTitular) {
        super(valor);
        if (numeroCartao == null || numeroCartao.replaceAll("[^0-9]","").length() != 16)
            throw new IllegalArgumentException("Numero do cartao invalido.");
        if (parcelas < 1 || parcelas > 12)
            throw new IllegalArgumentException("Numero de parcelas deve ser entre 1 e 12.");
        this.numeroCartao = numeroCartao.replaceAll("[^0-9]","");
        this.parcelas = parcelas;
        this.nomeTitular = nomeTitular;
    }

    @Override
    public void processar() {
        // Simulacao de processamento com banco
        System.out.println("  Processando pagamento no cartao final " + getUltimosDigitos() + "...");
        marcarComoProcessado();
        System.out.println("  Pagamento aprovado! " + parcelas + "x de R$ " +
                String.format("%.2f", getValor() / parcelas));
    }

    @Override
    public String getDescricao() {
        return "Cartao " + getUltimosDigitos() + " - " + parcelas + "x";
    }

    private String getUltimosDigitos() {
        return "****" + numeroCartao.substring(12);
    }

    public String getNumeroCartao() { return numeroCartao; }
    public int getParcelas() { return parcelas; }
    public String getNomeTitular() { return nomeTitular; }
    public void setNumeroCartao(String n) { this.numeroCartao = n; }
    public void setParcelas(int p) { this.parcelas = p; }
}
