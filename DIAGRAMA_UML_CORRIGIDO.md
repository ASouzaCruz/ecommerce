# Diagrama UML - Sistema E-commerce
---

    classDiagram

        %% ══════════════════════════════════════════════════════════════
        %% PRODUTO - Hierarquia de Herança
        %% ══════════════════════════════════════════════════════════════
        
        class Produto {
            <<abstract>>
            -String id
            -String nome
            -String descricao
            -double preco
            -boolean ativo
            +calcularFrete()* double
            +estaDisponivel()* boolean
            +getTipo()* String
            +getId() String
            +getNome() String
            +getDescricao() String
            +getPreco() double
            +isAtivo() boolean
            +setId(String) void
            +setAtivo(boolean) void
            +setDescricao(String) void
            +setNome(String) void
            +setPreco(double) void
            +toString() String
            
        }

        class ProdutoFisico {
            -double pesoKg
            -int estoque
            -double TARIFA_KG$
            -validarPeso(double) void
            +calcularFrete() double
            +estaDisponivel() boolean
            +getTipo() String
            +reduzirEstoque(int) void
            +aumentarEstoque(int) void
            +getPesoKg() double
            +getPeso(UnidadPeso) double
            +setPesoKg(double) void
            +setPeso(double, UnidadePeso) void
            +getEstoque() int
            +setEstoque(int) void
            +toString() String
        }

        class ProdutoDigital {
            -String linkDownload
            -String formato
            +calcularFrete() double
            +estaDisponivel() boolean
            +getTipo() String
            +getLinkDownload() String
            +getFormato() String
            +setLinkDownload(String) void
            +setFormato(String) void
            +toString() String
        }

        Produto <|-- ProdutoFisico
        Produto <|-- ProdutoDigital

        %% ═══════════════════════════════
        %% USUÁRIO - Hierarquia de Herança
        %% ═══════════════════════════════
        
        class Usuario {
            <<abstract>>
            -String id
            -String nome
            -String email
            -String senha
            +podeGerenciarProdutos()* boolean
            +podeVisualizarRelatorios()* boolean
            +getPerfil()* String
            +getId() String
            +getNome() String
            +getEmail() String
            +getSenha() String
            +autenticar(String) boolean
            +toString() String
        }

        class Cliente {
            -String cpf
            -List~Endereco~ enderecos
            +podeGerenciarProdutos() boolean
            +podeVisualizarRelatorios() boolean
            +getPerfil() String
            +adicionarEndereco(Endereco) void
            +removerEndereco(String) void
            +getCpf() String
            +getEnderecos() List
            +getEnderecoEntrega() Endereco
        }

        class Administrador {
            -String cargo
            -boolean superAdmin
            +podeGerenciarProdutos() boolean
            +podeVisualizarRelatorios() boolean
            +getPerfil() String
            +getCargo() String
            +isSUperAdmin() boolean
            +setCargo(String) void
            +podeRemoverAdmins() boolean
        }

        Usuario <|-- Cliente
        Usuario <|-- Administrador

        %% ══════════════════════════════════
        %% PAGAMENTO - Hierarquia de Herança
        %% ══════════════════════════════════
        
        class Pagamento {
            <<abstract>>
            -String id
            -double valor
            -LocalDateTime dataProcessamento
            -boolean processado
            +processar()* void
            +getDescricao()* String
            +marcarComoProcessado() void
            +getId() String
            +getValor() double
            +isProcessado() boolean
            +getDataProcessamento() LocalDateTime
            +setId(String) void
            +setProcessado(boolean) void
            +setDataProcessamento(LocalDateTime) void
            +toString() String
        }

        class PagamentoCartao {
            -String numeroCartao
            -int parcelas
            -String nomeTitular
            +processar() void
            +getDescricao() String
            -getUltimosDigitos() String
            +getNumeroCartao() String
            +getParcelas() int
            +getNomeTitular() String
            +setNumeroCartao(String) void
            +setParcelas(int) void
        }

        class PagamentoPix {
            -String chavePix
            -String codigoPix
            +processar() void
            +getDescricao() String
            -gerarCodigoPix() String
            +getChavePix() String
            +getCodigoPix() String
            +setChavePix(String) void
            +setCodigoPix(String) void
        }

        Pagamento <|-- PagamentoCartao
        Pagamento <|-- PagamentoPix

        %% ═══════════════════════════════════════════════
        %% ESTADO DO PEDIDO - Enum (não conta como classe)
        %% ═══════════════════════════════════════════════
        
        class EstadoPedido {
            <<enumeration>>
            ABERTO
            PAGO
            SEPARANDO
            ENVIADO
            ENTREGUE
            CANCELADO
            +podeTransicionarPara(EstadoPedido) boolean
        }

        %% ═══════════
        %% ENDEREÇO
        %% ═══════════
        
        class Endereco {
            -String rua
            -String numero
            -String cidade
            -String estado
            -String cep
            +getRua() String
            +getNumero() String
            +getBairro() String
            +getCidade() String
            +getEstado() String
            +getCep() String
            +setRua(String) void
            +setNumero(String) void
            +setBairro(String) void
            +setCidade(String) void
            +setEstado(String) void
            +setCep(String) void
            +toString() String
        }
    
        %% ════════════════
        %% ITEM DO PEDIDO
        %% ════════════════
        
        class ItemPedido {
            -Produto produto
            -int quantidade
            -double precoUnitario
            +calcularSubTotal() double
            +calcularFrete() double
            +getProduto () Produto
            +getQuantidade () int
            +getPrecoUnitario () int
            +setQuantidade (int) void
            +setPrecoUnitario(int) void
            +toString () String
        }

        %% ══════════════════════════════════════════════════════════════
        %% CARRINHO
        %% ══════════════════════════════════════════════════════════════
        
        class Carrinho {
            -List~ItemPedido~ itens
            +adicionarItem(Produto, int) void
            +removerItem(String) void
            +limpar() void
            +estaVazio() boolean
            +calcularTotal() double
            +calcularTotalFrete() double
            +getQuantidadeNoPedido(String) int
            +getItens() List
            +exibir() void
        }

        %% ══════════════════════════════════════════════════════════════
        %% PEDIDO
        %% ══════════════════════════════════════════════════════════════
        
        class Pedido {
            -String id
            -Cliente cliente
            -Pagamento pagamento
            -Endereco enderecoEntrega
            -EstadoPedido estado
            -LocalDateTime dataCriacao
            -LocalDateTime dataAtualizacao
            -List~ItemPedido~ itens
            +avancarEstado(EstadoPedido) void
            +registrarPagamento(Pagamento) void
            +cancelar() void
            +calcularTotal() double
            +calcularTotalFrete() double
            +calcularTotalComFrete() double
            +getId() String
            +getCliente() Cliente
            +getItens() List
            +getEstado() EstadoPedido
            +getPagamento() Pagamento
            +getDataCriacao() LocalDateTime
            +getDataAtualizacao() LocalDateTime
            +getEndereco() Endereco
            +setId(String) void
            +setEstado(EstadoPedido) void
            +setDataCriacao(LocalDateTime) void
            +setDataAtualizacao(LocalDateTime) void
            +toString() String
        }   

        %% ══════════════════════════════════════════════════════════════
        %% UNIDADE DE PESO - Enum (não conta como classe)
        %% ══════════════════════════════════════════════════════════════
        class UnidadePeso {
            <<enumeration>>
            GRAMA
            QUILOGRAMA
            <<final>> -String simbolo
            <<final>> -double fatorCOnversaoParaKg
            +converterParaKg (double) double
            +converterDeKg (double) double
            +porSimbolo (String) UnidadePeso
            +getSimbolo () String
            +getFatorConversao () double
            +toString () String
        }


        %% ══════════════════════════════════════════════════════════════
        %% REPOSITORIES
        %% ══════════════════════════════════════════════════════════════
        
        class ProdutoRepository {
            -List~Produto~ produtos
            +salvar(Produto) void
            +buscarPorId(String) Optional
            +buscarPorIdOuErro(String) Produto
            +buscarPorId(String) Optional
            +buscarPorNomeOuErro(String) Produto
            +buscarPorNome(String) Optional
            +listarTodos() List
            +listarDisponiveis() List
            +remover(String) void
            +setProdutos(List) void
        }

        class UsuarioRepository {
            -List~Usuario~ usuarios
            +salvar(Usuario) void
            +buscarPorEmail(String) Optional
            +buscarPorEmailOuErro(String) Usuario
            +buscarPorId(String) Optional
            +listarTodos() List
            +remover(String) void
            +setUsuarios(List) void
        }

        class PedidoRepository {
            -List~Pedido~ pedidos
            +salvar(Pedido) void
            +buscarPorId(String) Optional
            +buscarPorIdOuErro(String) Pedido
            +listarTodos() List
            +listarPorCliente(String) List
            +setPedidos(List) void
        }

        %% ══════════════════════════════════════════════════════════════
        %% SERVIÇO DE PERSISTÊNCIA
        %% ══════════════════════════════════════════════════════════════
        
        class PersistenciaService {
            <<Final>> -String DIR$
            <<Final>> -String ARQ_PRODUTOS$
            <<Final>> -String ARQ_USUARIOS$
            <<Final>> -String ARQ_PEDIDOS$
            <<Final>> -String ARQ_ITENS$
            <<Final>> -String ARQ_ENDERECOS$
            <<Final>> -String SEP
            +salvarProdutos(List) void
            +carregarProdutos() List
            +salvarUsuarios(List) void
            +carregarUsuarios() List
            +salvarPedidos(List) void
            +carregarPedidos(List, List) List
        }

        %% ══════════════════════════════════════════════════════════════
        %% TESTE
        %% ══════════════════════════════════════════════════════════════

        class TesteUnidadePeso {
            <<static>> -testeConversaoGramaParaKg() void
            <<static>> -testeConversaoKgParaGrama() void
            <<static>> -testeCriacaoProdutoComGramas() void
            <<static>> -testeCriacaoProdutoComQuilogramas() void
            <<static>> -testeCalculoFrete() void
            <<static>> -testeValidacaoPesoInvalido() void
            <<static>> -testeExtremo() void
        }

        %% ══════════════════════════════════════════════════════════════
        %% INTERFACE DE USUÁRIO
        %% ══════════════════════════════════════════════════════════════
        
        class Menu {
            -Scanner sc
            -Usuario usuarioLogado
            -Carrinho carrinho
            <<final>> -ProdutoRepository produtoRepo
            <<final>> -UsuarioRepository usuarioRepo
            <<final>> -PedidoRepository pedidoRepo
            <<final>> -PersistenciaService persistenciaService
            -imprimirTitulo(String) void
            -imprimirOpcoes(String) void
            -imprimirSeparado(String) void
            +iniciar() void
            -menuLogin() void
            -fazerLogin() void
            -cadastrarCliente() void
            -menuPrincipal() void
            -listarProdutos() void
            -menuProdutos() void
            -cadastrarProduto() void
            -editarProduto() void
            -removerProduto() void
            -listarTodosProdutos() void
            -obterUniadePeso() UnidadePeso
            -menuCarrinho() void
            -adicionarCarrinho() void
            -finalizarPedido() void
            -cadastrarEndereco(Cliente) void
            -listarMeusPedidos() void
            -avancarEstadopedido() void
            -cancelarPedido() void
            -exibirRelatorio() void
            -salvarDados() void
        }

        %% ══════════════════════════════════════════════════════════════
        %% EXCEÇÕES PERSONALIZADAS
        %% ══════════════════════════════════════════════════════════════
        
        class EstoqueInsuficienteException {
            <<exception>>
            +EstoqueInsuficienteException(String, int, int)
        }

        class PedidoInvalidoException {
            <<exception>>
            +PedidoInvalidoException(String)
        }

        class CarrinhoVazioException {
            <<exception>>
            +CarrinhoVazioException()
        }

        class ProdutoNaoEncontradoException {
            <<exception>>
            +ProdutoNaoEncontradoException(String)
        }

        class UsuarioNaoEncontradoException {
            <<exception>>
            +UsuarioNaoEncontradoException(String)
        }

        %% ══════════════════════════════════════════════════════════════
        %% RELACIONAMENTOS E ASSOCIAÇÕES
        %% ══════════════════════════════════════════════════════════════

        %% ItemPedido → Produto
        ItemPedido "1" --> "1" Produto : referencia

        %% Carrinho → ItemPedido
        Carrinho "1" o-- "0..*" ItemPedido : contem

        %% Pedido → Cliente (navegação)
        Pedido "1" --> "1" Cliente : pertence a

        %% Pedido → ItemPedido
        Pedido "1" o-- "1..*" ItemPedido : contem

        %% Pedido → EstadoPedido
        Pedido "1" --> "1" EstadoPedido : tem estado

        %% Pedido → Pagamento
        Pedido "1" --> "0..1" Pagamento : pago com

        %% Pedido → Endereco
        Pedido "1" --> "1" Endereco : entrega em

        %% Cliente → Endereco
        Cliente "1" o-- "0..*" Endereco : possui

        %% Menu → Usuario
        Menu "1" --> "0..1" Usuario : usuarioLogado

        %% Menu → Carrinho
        Menu "1" --> "1" Carrinho : gerencia

        %% Repositories
        Menu --> ProdutoRepository : usa
        Menu --> UsuarioRepository : usa
        Menu --> PedidoRepository : usa

        %% PersistenciaService
        Menu --> PersistenciaService : usa para salvar

        %% Exceções lançadas
        ProdutoFisico ..> EstoqueInsuficienteException : lança
        Pedido ..> PedidoInvalidoException : lança
        Carrinho ..> CarrinhoVazioException : lança
        ProdutoRepository ..> ProdutoNaoEncontradoException : lança
        UsuarioRepository ..> UsuarioNaoEncontradoException : lança
