package com.ecommerce.repository;

import com.ecommerce.exception.PedidoInvalidoException;
import com.ecommerce.model.Pedido;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PedidoRepository {
    private List<Pedido> pedidos = new ArrayList<>();

    public void salvar(Pedido pedido) { pedidos.add(pedido); }

    public Optional<Pedido> buscarPorId(String id) {
        return pedidos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public Pedido buscarPorIdOuErro(String id) {
        return buscarPorId(id).orElseThrow(() -> new PedidoInvalidoException("Pedido nao encontrado: " + id));
    }

    public List<Pedido> listarTodos() { return new ArrayList<>(pedidos); }

    public List<Pedido> listarPorCliente(String clienteId) {
        return pedidos.stream()
                .filter(p -> p.getCliente().getId().equals(clienteId))
                .collect(Collectors.toList());
    }

    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}