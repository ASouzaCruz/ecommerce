package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.produto.Produto;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class ProdutoRepository {

    private List<Produto> produtos;

    public void salvar(){

    }

    public Optional<Produto> buscarPorId(String id){
        return null;
    }

    public Produto buscarPorIdOuErro(String id){
        return null;
    }

    public List<Produto> listarTodos(){
        return produtos;
    }

    public List<Produto> listarDisponiveis(){
        return produtos;
    }

    public void remover(String id){

    }
}
