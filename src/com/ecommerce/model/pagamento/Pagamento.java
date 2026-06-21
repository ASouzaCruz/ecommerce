package com.ecommerce.model.pagamento;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Pagamento {
    private String id;
    private double valor;
    private LocalDateTime dataProcessamento;
    private boolean processado;

    public Pagamento(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor do pagamento deve ser positivo.");
        this.id = UUID.randomUUID().toString();
        this.valor = valor;
        this.processado = false;
    }

    // Polimorfismo 3 - cada meio de pagamento processa diferente
    public abstract void processar();
    public abstract String getDescricao();

    public void marcarComoProcessado() {
        this.processado = true;
        this.dataProcessamento = LocalDateTime.now();
    }

    public String getId() { return id; }
    public double getValor() { return valor; }
    public boolean isProcessado() { return processado; }
    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    public void setId(String id) { this.id = id; }
    public void setProcessado(boolean p) { this.processado = p; }
    public void setDataProcessamento(LocalDateTime d) { this.dataProcessamento = d; }

    @Override
    public String toString() {
        return String.format("[%s] R$ %.2f | %s", getDescricao(), valor,
                processado ? "Aprovado" : "Pendente");
    }
}
