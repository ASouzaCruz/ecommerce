package com.ecommerce.model.pagamento;

import java.time.LocalDateTime;

// Implementação inicial baseada no diagrama UML. Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class PagamentoCartao extends Pagamento {

    private String numeroCartao;
    private int parcelas;
    private String nomeTitular;

    public PagamentoCartao(String id, double valor, LocalDateTime dataProcessamento, boolean processado, String numeroCartao, int parcelas, String nomeTitular) {
        super(id, valor, dataProcessamento, processado);
        this.numeroCartao = numeroCartao;
        this.parcelas = parcelas;
        this.nomeTitular = nomeTitular;
    }

    public void processar(){

    }

    public String getDescricao(){
        return "Teste"; 
    }

    public boolean validarCartao(){
        return true;
    }
}
