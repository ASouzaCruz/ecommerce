package com.ecommerce.model.pagamento;

import java.time.LocalDateTime;

// Implementação inicial baseada no diagrama UML. Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class PagamentoPix extends Pagamento {


    private String chavePix;
    private String codigoPix;

    public PagamentoPix(String id, double valor, LocalDateTime dataProcessamento, boolean processado, String codigoPix, String chavePix) {
        super(id, valor, dataProcessamento, processado);
        this.chavePix = chavePix;
        this.codigoPix = codigoPix;
    }

    @Override
    public void processar(){

    } 

    @Override
    public String getDescricao(){
        return "Teste"; 
    }
    
    private String gerarCodigoPix(){
        return "codigo aleatorio";
    }

}
