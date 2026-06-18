package com.ecommerce.model.pagamento;

import java.time.LocalDateTime;

// Implementação inicial baseada no diagrama UML. Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public abstract class Pagamento {

    private String id;
    private double valor;
    private LocalDateTime dataProcessamento;
    private boolean processado;

    public Pagamento(String id, double valor, LocalDateTime dataProcessamento, boolean processado){
        this.id = id;
        this.valor = valor;
        this.dataProcessamento = dataProcessamento;
        this.processado = processado;
    }

    public abstract void processar();

    public abstract String getDescricao();

    // qual o sentido desta funcao quando ja existe processar?

    public void marcarComoProcessado(){
    }

    //

    public String getId(){
        return id;
    }

    public double getValor(){
        return valor;
    }
    
}
