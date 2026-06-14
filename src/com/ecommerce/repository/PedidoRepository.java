package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.Pedido;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class PedidoRepository {

    private List<Pedido> pedidos;

    public void salvar(String id){
    }

    public Optional<Pedido> buscarPorId(String id){
        return null;
    }

    public List<Pedido> listarTodos(){
        return pedidos;
    }

    public List<Pedido> listarPorCliente(String id){
        return pedidos;
    }

}
