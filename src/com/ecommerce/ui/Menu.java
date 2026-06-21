package com.ecommerce.ui;

import com.ecommerce.exception.*;
import com.ecommerce.model.*;
import com.ecommerce.model.pagamento.*;
import com.ecommerce.model.produto.*;
import com.ecommerce.model.usuario.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.PersistenciaService;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final ProdutoRepository produtoRepo;
    private final UsuarioRepository usuarioRepo;
    private final PedidoRepository pedidoRepo;
    private final PersistenciaService persistencia;
    private Usuario usuarioLogado;
    private Carrinho carrinho = new Carrinho();

    public Menu(ProdutoRepository pr, UsuarioRepository ur, PedidoRepository pedr, PersistenciaService ps) {
        this.produtoRepo = pr;
        this.usuarioRepo = ur;
        this.pedidoRepo = pedr;
        this.persistencia = ps;
    }

    // ────── UTILIDADES DE FORMATAÇÃO ──────────────────────────────────────────
    private void imprimirTitulo(String titulo) {
        System.out.println("\n╔" + "═".repeat(titulo.length() + 2) + "╗");
        System.out.println("║ " + titulo + " ║");
        System.out.println("╚" + "═".repeat(titulo.length() + 2) + "╝");
    }

    private void imprimirOpcoes(String... opcoes) {
        for (String opcao : opcoes) {
            System.out.println("  " + opcao);
        }
    }

    private void imprimirSeparador() {
        System.out.println("  " + "─".repeat(50));
    }

    public void iniciar() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║     BEM-VINDO AO SISTEMA ECOMMERCE POO       ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        while (true) {
            if (usuarioLogado == null) menuLogin();
            else menuPrincipal();
        }
    }

    // ── LOGIN ─────────────────────────────────────────────────────────────────
    private void menuLogin() {
        imprimirTitulo("AUTENTICAÇÃO");
        imprimirOpcoes(
            "[1] Login",
            "[2] Cadastrar novo usuário",
            "[0] Sair do sistema"
        );
        imprimirSeparador();
        System.out.print("  Escolha uma opção: ");
        switch (sc.nextLine().trim()) {
            case "1" -> fazerLogin();
            case "2" -> cadastrarCliente();
            case "0" -> { salvarDados(); System.out.println("\n  Até logo!"); System.exit(0); }
            default  -> System.out.println("  ⚠ Opção inválida. Tente novamente.");
        }
    }

    private void fazerLogin() {
        System.out.println();
        System.out.print("  Email:  "); 
        String email = sc.nextLine().trim();
        System.out.print("  Senha:  "); 
        String senha = sc.nextLine().trim();
        try {
            Usuario u = usuarioRepo.buscarPorEmailOuErro(email);
            if (!u.autenticar(senha)) { 
                System.out.println("  ❌ Senha incorreta."); 
                return; 
            }
            usuarioLogado = u;
            System.out.println("  ✓ Bem-vindo, " + u.getNome() + "! [" + u.getPerfil() + "]");
        } catch (UsuarioNaoEncontradoException e) {
            System.out.println("  ❌ Usuário não encontrado.");
        }
    }

    private void cadastrarCliente() {
        imprimirTitulo("CADASTRO DE NOVO CLIENTE");
        try {
            System.out.println();
            System.out.print("  Nome:                   "); 
            String nome = sc.nextLine().trim();
            System.out.print("  Email:                  "); 
            String email = sc.nextLine().trim();
            System.out.print("  Senha:                  "); 
            String senha = sc.nextLine().trim();
            System.out.print("  CPF (somente números):  "); 
            String cpf = sc.nextLine().trim();
            Cliente c = new Cliente(nome, email, senha, cpf);
            usuarioRepo.salvar(c);
            System.out.println("\n  ✓ Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        }
    }

    // ── MENU PRINCIPAL ────────────────────────────────────────────────────────
    private void menuPrincipal() {
        imprimirTitulo("MENU PRINCIPAL [" + usuarioLogado.getPerfil() + "]");
        
        imprimirOpcoes(
            "[1] Ver produtos",
            "[2] Carrinho de compras",
            "[3] Meus pedidos"
        );
        
        if (usuarioLogado.podeGerenciarProdutos()) {
            imprimirSeparador();
            imprimirOpcoes(
                "[4] Gerenciar produtos",
                "[5] Gerenciar pedidos",
                "[6] Relatório de vendas"
            );
        }
        
        imprimirSeparador();
        imprimirOpcoes(
            "[9] Logout",
            "[0] Sair do sistema"
        );
        
        imprimirSeparador();
        System.out.print("  Escolha uma opção: ");
        switch (sc.nextLine().trim()) {
            case "1" -> listarProdutos();
            case "2" -> menuCarrinho();
            case "3" -> listarMeusPedidos();
            case "4" -> { if (usuarioLogado.podeGerenciarProdutos()) menuProdutos(); }
            case "5" -> { if (usuarioLogado.podeGerenciarProdutos()) menuPedidosAdmin(); }
            case "6" -> { if (usuarioLogado.podeVisualizarRelatorios()) exibirRelatorio(); }
            case "9" -> { usuarioLogado = null; carrinho.limpar(); System.out.println("\n  ✓ Logout efetuado."); }
            case "0" -> { salvarDados(); System.out.println("\n  Até logo!"); System.exit(0); }
            default  -> System.out.println("  ⚠ Opção inválida.");
        }
    }

    // ── PRODUTOS ──────────────────────────────────────────────────────────────
    private void listarProdutos() {
        List<Produto> lista = produtoRepo.listarDisponiveis();
        if (lista.isEmpty()) { 
            System.out.println("  ⚠ Nenhum produto disponível."); 
            return; 
        }
        imprimirTitulo("PRODUTOS DISPONÍVEIS");
        System.out.println();
        for (int i = 0; i < lista.size(); i++)
            System.out.printf("  [%d] %s%n", i + 1, lista.get(i));
    }

    private void menuProdutos() {
        imprimirTitulo("GERENCIAR PRODUTOS");
        imprimirOpcoes(
            "[1] Cadastrar novo produto",
            "[2] Editar produto existente",
            "[3] Remover produto",
            "[0] Voltar"
        );
        imprimirSeparador();
        System.out.print("  Escolha uma opção: ");
        switch (sc.nextLine().trim()) {
            case "1" -> cadastrarProduto();
            case "2" -> editarProduto();
            case "3" -> removerProduto();
        }
    }

    private void cadastrarProduto() {
        imprimirTitulo("CADASTRO DE NOVO PRODUTO");
        try {
            System.out.println();
            System.out.println("  Tipo de Produto:");
            System.out.println("    [1] Produto Físico");
            System.out.println("    [2] Produto Digital");
            System.out.print("  Escolha: ");
            String tipo = sc.nextLine().trim();
            
            System.out.println();
            System.out.print("  Nome:        "); 
            String nome = sc.nextLine().trim();
            System.out.print("  Descrição:   "); 
            String desc = sc.nextLine().trim();
            System.out.print("  Preço (R$):  "); 
            double preco = Double.parseDouble(sc.nextLine().trim());
            
            Produto p;
            if ("1".equals(tipo)) {
                System.out.println();
                // Solicita unidade de peso
                UnidadePeso unidade = obterUnidadePeso();
                System.out.print("  Peso (" + unidade.getSimbolo() + "):  ");
                double peso = Double.parseDouble(sc.nextLine().trim());
                System.out.print("  Estoque:     ");   
                int est = Integer.parseInt(sc.nextLine().trim());
                p = new ProdutoFisico(nome, desc, preco, peso, unidade, est);
            } else {
                System.out.println();
                System.out.print("  Link download:       "); 
                String link = sc.nextLine().trim();
                System.out.print("  Formato de arquivo:  "); 
                String fmt = sc.nextLine().trim();
                p = new ProdutoDigital(nome, desc, preco, link, fmt);
            }
            produtoRepo.salvar(p);
            System.out.println("\n  ✓ Produto cadastrado com sucesso!");
            System.out.println("  ID: " + p.getId());
        } catch (NumberFormatException e) {
            System.out.println("  ❌ Erro: Número inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        }
    }

    private void editarProduto() {
        imprimirTitulo("EDITAR PRODUTO");
        System.out.println();
        listarTodosProdutos();
        System.out.println();
        System.out.print("  ID do produto: "); 
        String id = sc.nextLine().trim();
        try {
            Produto p = produtoRepo.buscarPorIdOuErro(id);
            System.out.println("\n  Produto atual:");
            System.out.println("    " + p);
            System.out.println();
            
            System.out.print("  Novo nome (" + p.getNome() + "): "); 
            String nome = sc.nextLine().trim();
            System.out.print("  Novo preço (R$ " + p.getPreco() + "): "); 
            String precoStr = sc.nextLine().trim();
            
            if (!nome.isBlank()) p.setNome(nome);
            if (!precoStr.isBlank()) p.setPreco(Double.parseDouble(precoStr));
            
            if (p instanceof ProdutoFisico f) {
                System.out.print("  Novo estoque (" + f.getEstoque() + "): "); 
                String est = sc.nextLine().trim();
                if (!est.isBlank()) f.setEstoque(Integer.parseInt(est));
                
                // Opção para editar peso
                System.out.print("  Deseja alterar o peso? (s/n): ");
                if ("s".equalsIgnoreCase(sc.nextLine().trim())) {
                    System.out.println();
                    System.out.println("    Peso atual: " + f.getPesoKg() + " kg");
                    UnidadePeso unidade = obterUnidadePeso();
                    System.out.print("    Novo peso (" + unidade.getSimbolo() + "): ");
                    double novoPeso = Double.parseDouble(sc.nextLine().trim());
                    f.setPeso(novoPeso, unidade);
                    System.out.println("    ✓ Peso atualizado para " + novoPeso + " " + unidade.getSimbolo() + " (" + f.getPesoKg() + " kg)");
                }
            }
            System.out.println("\n  ✓ Produto atualizado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("  ❌ Erro: Número inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        }
    }

    private void removerProduto() {
        imprimirTitulo("REMOVER PRODUTO");
        System.out.println();
        listarTodosProdutos();
        System.out.println();
        System.out.print("  ID do produto: "); 
        String id = sc.nextLine().trim();
        try {
            produtoRepo.remover(id);
            System.out.println("  ✓ Produto removido com sucesso.");
        } catch (Exception e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        }
    }

    private void listarTodosProdutos() {
        System.out.println("  Produtos disponíveis:");
        List<Produto> todos = produtoRepo.listarTodos();
        if (todos.isEmpty()) {
            System.out.println("    (nenhum produto cadastrado)");
        } else {
            for (Produto p : todos) {
                System.out.println("    [" + p.getId() + "] " + p.getNome() + " - R$ " + p.getPreco());
            }
        }
    }

    /**
     * Solicita ao usuário que escolha a unidade de peso (kg ou g).
     * Retorna a unidade escolhida com validação.
     */
    private UnidadePeso obterUnidadePeso() {
        System.out.println();
        System.out.println("  Escolha a unidade de peso:");
        System.out.println("    [1] Quilogramas (kg)");
        System.out.println("    [2] Gramas (g)");
        System.out.print("  Opção: ");
        String escolha = sc.nextLine().trim();
        
        UnidadePeso unidade = switch (escolha) {
            case "1" -> UnidadePeso.QUILOGRAMA;
            case "2" -> UnidadePeso.GRAMA;
            default -> {
                System.out.println("  ⚠ Opção inválida. Usando quilogramas por padrão.");
                yield UnidadePeso.QUILOGRAMA;
            }
        };
        return unidade;
    }

    // ── CARRINHO ──────────────────────────────────────────────────────────────
    private void menuCarrinho() {
        imprimirTitulo("SEU CARRINHO DE COMPRAS");
        System.out.println();
        carrinho.exibir();
        System.out.println();
        imprimirOpcoes(
            "[1] Adicionar produto",
            "[2] Remover produto",
            "[3] Finalizar pedido",
            "[0] Voltar"
        );
        imprimirSeparador();
        System.out.print("  Escolha uma opção: ");
        switch (sc.nextLine().trim()) {
            case "1" -> adicionarAoCarrinho();
            case "2" -> removerDoCarrinho();
            case "3" -> finalizarPedido();
        }
    }

    private void adicionarAoCarrinho() {
        listarProdutos();
        System.out.println();
        System.out.print("  Digite o NOME do produto: ");
        String nome = sc.nextLine().trim();
        System.out.print("  Quantidade:   ");
        int qtd = Integer.parseInt(sc.nextLine().trim());
        try {
            Produto p = produtoRepo.buscarPorNomeOuErro(nome);
            carrinho.adicionarItem(p, qtd);
            System.out.println("  ✓ Produto adicionado ao carrinho!");
        } catch (Exception e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        }
    }

    private void removerDoCarrinho() {
        imprimirTitulo("REMOVER DO CARRINHO");
        System.out.println();
        carrinho.exibir();
        System.out.println();
        System.out.print("  ID do produto para remover: "); 
        String id = sc.nextLine().trim();
        try {
            carrinho.removerItem(id);
            System.out.println("  ✓ Item removido do carrinho.");
        } catch (Exception e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        }
    }

    private void finalizarPedido() {
        
        if (!(usuarioLogado instanceof Cliente cliente)) {
            System.out.println("  ⚠ Apenas clientes podem fazer pedidos."); 
            return;
        }
        if (carrinho.estaVazio()) { 
            System.out.println("  ⚠ Carrinho vazio!"); 
            return; 
        }
        try {
            Endereco enderecoEscolhido ;
            if (cliente.getEnderecos().isEmpty()) {
                System.out.println("⚠ Nenhum endereço cadastrado.");
                cadastrarEndereco(cliente);
                enderecoEscolhido = cliente.getEnderecoEntrega();
            } else {

                System.out.println("\nEndereços cadastrados:");

                List<Endereco> enderecos = cliente.getEnderecos();

                for (int i = 0; i < enderecos.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + enderecos.get(i));
                }

                System.out.println("[0] Cadastrar novo endereço");

                System.out.print("Escolha: ");
                int opcao = Integer.parseInt(sc.nextLine());

                if (opcao == 0) {
                    cadastrarEndereco(cliente);
                    enderecoEscolhido = cliente.getEnderecos().get(cliente.getEnderecos().size() - 1);
                } else {
                    enderecoEscolhido = enderecos.get(opcao - 1);
                }
            }

            imprimirTitulo("FINALIZAR PEDIDO");
            Pedido pedido = new Pedido(cliente, carrinho.getItens(), enderecoEscolhido);
            System.out.println();
            System.out.printf("  Subtotal:        R$ %.2f%n", pedido.calcularTotal());
            System.out.printf("  Frete:           R$ %.2f%n", pedido.calcularTotalFrete());
            System.out.printf("  ───────────────────────────────%n");
            System.out.printf("  TOTAL:           R$ %.2f%n", pedido.calcularTotalComFrete());
            System.out.println();
            System.out.println("  Escolha forma de pagamento:");
            System.out.println("    [1] Pagar com Pix");
            System.out.println("    [2] Pagar com Cartão");
            System.out.println("    [0] Cancelar");
            System.out.print("  Opção: ");
            Pagamento pg = switch (sc.nextLine().trim()) {
                case "1" -> { 
                    yield new PagamentoPix(pedido.calcularTotalComFrete());
                }
                case "2" -> {
                    System.out.println();
                    System.out.print("  Número cartão (16 dígitos): "); 
                    String num = sc.nextLine().trim();
                    System.out.print("  Parcelas (1-12):             "); 
                    int par = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("  Nome do titular:             "); 
                    String tit = sc.nextLine().trim();
                    yield new PagamentoCartao(pedido.calcularTotalComFrete(), num, par, tit);
                }
                default -> null;
            };
            if (pg == null) { 
                System.out.println("  ⚠ Pedido cancelado."); 
                return; 
            }
            pedido.registrarPagamento(pg);
            // Reduz estoque dos produtos fisicos
            for (ItemPedido item : carrinho.getItens()) {
                if (item.getProduto() instanceof ProdutoFisico f)
                    f.reduzirEstoque(item.getQuantidade());
            }
            pedidoRepo.salvar(pedido);
            carrinho.limpar();
            System.out.println("\n  ✓ Pedido criado com sucesso!");
            System.out.println("  ID: " + pedido.getId());
        } catch (Exception e) {
            System.out.println("  ❌ Erro ao finalizar pedido: " + e.getMessage());
        }
    }

    private void cadastrarEndereco(Cliente cliente) {
        imprimirTitulo("CADASTRO DE ENDEREÇO");
        try {
            System.out.println();
            System.out.print("  Rua:     "); 
            String rua = sc.nextLine().trim();
            System.out.print("  Número:  ");  
            String num = sc.nextLine().trim();
            System.out.print("  Bairro:  ");  
            String bairro = sc.nextLine().trim();
            System.out.print("  Cidade:  ");  
            String cidade = sc.nextLine().trim();
            System.out.print("  Estado:  ");  
            String estado = sc.nextLine().trim();
            System.out.print("  CEP:     ");     
            String cep = sc.nextLine().trim();
            cliente.adicionarEndereco(new Endereco(rua, num, bairro, cidade, estado, cep));
            System.out.println("\n  ✓ Endereço cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        }
    }

    // ── PEDIDOS ───────────────────────────────────────────────────────────────
    private void listarMeusPedidos() {
        if (!(usuarioLogado instanceof Cliente cl)) { 
            System.out.println("  ⚠ Apenas clientes."); 
            return; 
        }
        List<Pedido> lista = pedidoRepo.listarPorCliente(cl.getId());
        if (lista.isEmpty()) { 
            System.out.println("  ⚠ Nenhum pedido encontrado."); 
            return; 
        }
        imprimirTitulo("MEUS PEDIDOS");
        System.out.println();
        lista.forEach(p -> System.out.println("  " + p));
    }

    private void menuPedidosAdmin() {
        imprimirTitulo("GERENCIAMENTO DE PEDIDOS");
        System.out.println();
        List<Pedido> todos = pedidoRepo.listarTodos();
        if (todos.isEmpty()) {
            System.out.println("  (nenhum pedido cadastrado)");
        } else {
            todos.forEach(p -> System.out.println("  " + p));
        }
        System.out.println();
        imprimirOpcoes(
            "[1] Avançar estado do pedido",
            "[2] Cancelar pedido",
            "[0] Voltar"
        );
        imprimirSeparador();
        System.out.print("  Escolha uma opção: ");
        switch (sc.nextLine().trim()) {
            case "1" -> avancarEstadoPedido();
            case "2" -> cancelarPedido();
        }
    }

    private void avancarEstadoPedido() {
        System.out.println();
        System.out.print("  ID do pedido: "); 
        String id = sc.nextLine().trim();
        try {
            Pedido p = pedidoRepo.buscarPorIdOuErro(id);
            System.out.println("  Estado atual: " + p.getEstado());
            System.out.println("  Novo estado (SEPARANDO/ENVIADO/ENTREGUE): ");
            EstadoPedido novo = EstadoPedido.valueOf(sc.nextLine().trim().toUpperCase());
            p.avancarEstado(novo);
            System.out.println("  ✓ Estado atualizado para: " + p.getEstado());
        } catch (Exception e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        }
    }

    private void cancelarPedido() {
        System.out.println();
        System.out.print("  ID do pedido: "); 
        String id = sc.nextLine().trim();
        try {
            pedidoRepo.buscarPorIdOuErro(id).cancelar();
            System.out.println("  ✓ Pedido cancelado.");
        } catch (Exception e) {
            System.out.println("  ❌ Erro: " + e.getMessage());
        }
    }

    private void exibirRelatorio() {
        List<Pedido> todos = pedidoRepo.listarTodos();
        double totalVendas = todos.stream().mapToDouble(Pedido::calcularTotalComFrete).sum();
        
        imprimirTitulo("RELATÓRIO DE VENDAS");
        System.out.println();
        System.out.println("  📊 INFORMAÇÕES GERAIS");
        System.out.println("  " + "─".repeat(48));
        System.out.printf("  Total de pedidos:       %d%n", todos.size());
        System.out.printf("  Faturamento total:     R$ %.2f%n", totalVendas);
        System.out.printf("  Produtos cadastrados:  %d%n", produtoRepo.listarTodos().size());
        System.out.printf("  Clientes cadastrados:  %d%n", usuarioRepo.listarTodos().size()-1);
        System.out.println();
    }

    private void salvarDados() {
        persistencia.salvarProdutos(produtoRepo.listarTodos());
        persistencia.salvarUsuarios(usuarioRepo.listarTodos());
        persistencia.salvarPedidos(pedidoRepo.listarTodos(), produtoRepo.listarTodos());
        System.out.println("\n  ✓ Dados salvos com sucesso.");
    }
}
