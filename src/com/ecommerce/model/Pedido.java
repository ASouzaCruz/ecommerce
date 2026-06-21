package com.ecommerce.model;

import com.ecommerce.exception.PedidoInvalidoException;
import com.ecommerce.exception.CarrinhoVazioException;
import com.ecommerce.model.pagamento.Pagamento;
import com.ecommerce.model.usuario.Cliente;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {
    private String id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private EstadoPedido estado;
    private Pagamento pagamento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Endereco enderecoEntrega;

    public Pedido(Cliente cliente, List<ItemPedido> itens, Endereco enderecoEntrega) {
        if (itens == null || itens.isEmpty()) throw new CarrinhoVazioException();
        if (enderecoEntrega == null) throw new IllegalArgumentException("Endereco de entrega obrigatorio.");
        this.id = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.itens = new ArrayList<>(itens);
        this.estado = EstadoPedido.ABERTO;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.enderecoEntrega = enderecoEntrega;
    }
    //construtor pra persistencia
    public Pedido(String id, Cliente cliente, EstadoPedido estado, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.cliente = cliente;
        this.estado = estado;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.itens = new ArrayList<>();
        this.enderecoEntrega = null; // Não temos isso no CSV atual
    }

    // Estado dinamico com regras de transicao
    public void avancarEstado(EstadoPedido novoEstado) {
        if (!estado.podeTransicionarPara(novoEstado))
            throw new PedidoInvalidoException(
                String.format("Transicao invalida: %s -> %s", estado, novoEstado));
        this.estado = novoEstado;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void registrarPagamento(Pagamento pagamento) {
        if (estado != EstadoPedido.ABERTO)
            throw new PedidoInvalidoException("Pagamento so pode ser registrado em pedidos ABERTOS.");
        pagamento.processar();
        this.pagamento = pagamento;
        avancarEstado(EstadoPedido.PAGO);
    }

    public void cancelar() {
        if (!estado.podeTransicionarPara(EstadoPedido.CANCELADO))
            throw new PedidoInvalidoException("Pedido no estado " + estado + " nao pode ser cancelado.");
        this.estado = EstadoPedido.CANCELADO;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public double calcularTotal() {
        return itens.stream().mapToDouble(ItemPedido::calcularSubtotal).sum();
    }

    public double calcularTotalFrete() {
        return itens.stream().mapToDouble(ItemPedido::calcularFrete).sum();
    }

    public double calcularTotalComFrete() {
        return calcularTotal() + calcularTotalFrete();
    }

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return itens; }
    public EstadoPedido getEstado() { return estado; }
    public Pagamento getPagamento() { return pagamento; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public Endereco getEnderecoEntrega() { return enderecoEntrega; }
    public void setId(String id) { this.id = id; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
    public void setDataCriacao(LocalDateTime data) { this.dataCriacao = data; }
    public void setDataAtualizacao(LocalDateTime data) { this.dataAtualizacao = data; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Pedido #%s | Cliente: %s | Estado: %s | Total: R$ %.2f | Data: %s",
                id.substring(0, 8), cliente.getNome(), estado, calcularTotalComFrete(),
                dataCriacao.format(fmt));
    }
}