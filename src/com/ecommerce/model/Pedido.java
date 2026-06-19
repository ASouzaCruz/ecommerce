package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class Pedido {

    private String id;
    private EstadoPedido estado;
    private LocalDateTime dataCriacao, dataAtualizacao;
    private List<ItemPedido> itens;

    public Pedido(String id, EstadoPedido estado, List<ItemPedido> itens) {
        this.id = id;
        this.estado = EstadoPedido.ABERTO;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.itens = itens;
    }

    public void avancarEstado(EstadoPedido novoEstado){

        if(estado.podeTransicionarPara(novoEstado)){
            this.estado = novoEstado;
            this.dataAtualizacao = LocalDateTime.now();
        }
        else{
            System.out.println("Transição de estado inválida!");
        }

    }

    public void registrarPagamento(){

        avancarEstado(EstadoPedido.PAGO);
        System.out.println("Pagamento realizado!");

    }

    public void cancelar(){

        avancarEstado(EstadoPedido.CANCELADO);
        System.out.println("Pedido cancelado!");
        
    }

    public double calcularTotal(){
        return 10;
    }

    public double calcularTotalFrete(){
        return 20;
    }

    public String getId(){
        return id;
    } 

    public EstadoPedido getEstado(){
        return estado;
    }

    // funcaos adicionadas depois

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}
