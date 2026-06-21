package com.ecommerce;

import com.ecommerce.model.usuario.*;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.produto.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.PersistenciaService;
import com.ecommerce.ui.Menu;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersistenciaService persistencia = new PersistenciaService();
        ProdutoRepository produtoRepo = new ProdutoRepository();
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        PedidoRepository pedidoRepo   = new PedidoRepository();

        // Carrega dados persistidos
        List<com.ecommerce.model.produto.Produto> produtos = persistencia.carregarProdutos();
        if (!produtos.isEmpty()) {
            produtoRepo.setProdutos(produtos);
            System.out.println(produtos.size() + " produto(s) carregado(s).");
        }

        List<com.ecommerce.model.usuario.Usuario> usuarios = persistencia.carregarUsuarios();
        if (!usuarios.isEmpty()) {
            usuarioRepo.setUsuarios(usuarios);
            System.out.println(usuarios.size() + " usuario(s) carregado(s).");
        }

        // Dados iniciais se nao houver nada salvo
        if (produtoRepo.listarTodos().isEmpty()) {
            produtoRepo.salvar(new ProdutoFisico("Teclado Mecanico", "Switch Blue, RGB", 350.0, 1.2, 15));
            produtoRepo.salvar(new ProdutoFisico("Mouse Gamer", "DPI ajustavel, 6 botoes", 180.0, 0.3, 30));
            produtoRepo.salvar(new ProdutoDigital("Curso Java POO", "40h de conteudo", 99.90, "https://cursos.com/java", "ZIP"));
            produtoRepo.salvar(new ProdutoDigital("Ebook Clean Code", "Boas praticas de codigo", 29.90, "https://ebooks.com/clean", "PDF"));
            System.out.println("Produtos de exemplo criados.");
        }

        if (usuarioRepo.listarTodos().isEmpty()) {
            usuarioRepo.salvar(new Administrador("Admin", "admin@loja.com", "admin123", "Gerente", true));
            usuarioRepo.salvar(new Cliente("Joao Silva", "joao@email.com", "senha123", "12345678901"));
            System.out.println("Usuarios de exemplo criados.");
        }

        // --- A SOLUÇÃO ESTÁ AQUI: CARREGAR OS PEDIDOS ---
        // Usamos listarTodos() dos repositórios para garantir que temos a lista completa (incluindo os de exemplo, se foram criados agora)
        List<Pedido> pedidos = persistencia.carregarPedidos(usuarioRepo.listarTodos(), produtoRepo.listarTodos());
        
        if (!pedidos.isEmpty()) {
            // OBS: Verifique se o seu PedidoRepository tem o método setPedidos() ou similar.
            // Caso tenha um nome diferente (ex: inicializarLista), ajuste a linha abaixo.
            pedidoRepo.setPedidos(pedidos); 
            System.out.println(pedidos.size() + " pedido(s) carregado(s).");
        }
        // ------------------------------------------------

        new Menu(produtoRepo, usuarioRepo, pedidoRepo, persistencia).iniciar();
    }
}
