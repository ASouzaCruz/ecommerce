package com.ecommerce.model;

import com.ecommerce.exception.EstoqueInsuficienteException;
import com.ecommerce.model.produto.Produto;
import com.ecommerce.model.produto.ProdutoFisico;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrinho {
    private List<ItemPedido> itens;

    public Carrinho() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Produto produto, int quantidade) {
        // Regra de negocio: produto indisponivel nao pode ser adicionado
        if (!produto.estaDisponivel())
            throw new IllegalStateException("Produto '" + produto.getNome() + "' nao esta disponivel para compra.");

        // Regra de negocio: verifica estoque para produtos fisicos
        if (produto instanceof ProdutoFisico fisico) {
            int jaNoCarrinho = getQuantidadeNoPedido(produto.getId());
            if (jaNoCarrinho + quantidade > fisico.getEstoque())
                throw new EstoqueInsuficienteException(produto.getNome(), fisico.getEstoque(), jaNoCarrinho + quantidade);
        }

        Optional<ItemPedido> existente = itens.stream()
                .filter(i -> i.getProduto().getId().equals(produto.getId()))
                .findFirst();

        if (existente.isPresent()) {
            existente.get().setQuantidade(existente.get().getQuantidade() + quantidade);
        } else {
            itens.add(new ItemPedido(produto, quantidade));
        }
    }

    public void removerItem(String produtoId) {
        boolean removido = itens.removeIf(i -> i.getProduto().getId().equals(produtoId));
        if (!removido) throw new IllegalArgumentException("Produto nao encontrado no carrinho.");
    }

    public void limpar() { itens.clear(); }

    public boolean estaVazio() { return itens.isEmpty(); }

    public double calcularTotal() {
        return itens.stream().mapToDouble(ItemPedido::calcularSubtotal).sum();
    }

    public double calcularFrete() {
        return itens.stream().mapToDouble(ItemPedido::calcularFrete).sum();
    }

    private int getQuantidadeNoPedido(String produtoId) {
        return itens.stream()
                .filter(i -> i.getProduto().getId().equals(produtoId))
                .mapToInt(ItemPedido::getQuantidade).sum();
    }

    public List<ItemPedido> getItens() { return new ArrayList<>(itens); }

    public void exibir() {
        if (estaVazio()) { System.out.println("  Carrinho vazio."); return; }
        itens.forEach(i -> System.out.println("  " + i));
        System.out.printf("  Subtotal: R$ %.2f | Frete: R$ %.2f | Total: R$ %.2f%n",
                calcularTotal(), calcularFrete(), calcularTotal() + calcularFrete());
    }
}