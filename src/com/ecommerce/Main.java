package com.ecommerce;

import java.util.ArrayList;

import com.ecommerce.model.Carrinho;
import com.ecommerce.model.ItemPedido;

public class Main {

    //fazendo apenas testes aleatorios, nao reflete o que sera feito de fato no projeto final

    public static void main(String[] args) {

        Carrinho carrinho = new Carrinho("C001", new ArrayList<>());

        System.out.println("Carrinho vazio? " + carrinho.estaVazio());

        ItemPedido item1 = new ItemPedido("I001", 2, 10.0, 5.0);
        ItemPedido item2 = new ItemPedido("I002", 1, 20.0, 8.0);

        carrinho.adicionarItem(item1);
        carrinho.adicionarItem(item2);

        System.out.println("Quantidade de itens: " + carrinho.getItens().size());

        System.out.println("Carrinho vazio? " + carrinho.estaVazio());

        carrinho.removerItem("I001");

        System.out.println("Quantidade após remover I001: "
                + carrinho.getItens().size());

        carrinho.limpar();

        System.out.println("Quantidade após limpar: "
                + carrinho.getItens().size());

        System.out.println("Carrinho vazio? " + carrinho.estaVazio());

        carrinho.limpar(); // testar carrinho já vazio
    }

}