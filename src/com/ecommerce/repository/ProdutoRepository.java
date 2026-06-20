package com.ecommerce.repository;

import com.ecommerce.exception.ProdutoNaoEncontradoException;
import com.ecommerce.model.produto.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class ProdutoRepository {

    private List<Produto> produtos = new ArrayList<>();

    public void salvar(Produto produto) {
        if (buscarPorId(produto.getId()).isPresent())
            throw new IllegalStateException("Produto ja cadastrado com este ID.");
        produtos.add(produto);
    }

    public Produto buscarPorIdOuErro(String id) {
        return buscarPorId(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public Optional<Produto> buscarPorId(String id) {
        return produtos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Produto> listarTodos() { return new ArrayList<>(produtos); }

    public List<Produto> listarDisponiveis() {
        return produtos.stream().filter(Produto::estaDisponivel).collect(Collectors.toList());
    }

    public void remover(String id) {
        Produto p = buscarPorIdOuErro(id);
        produtos.remove(p);
    }

    public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }
}
