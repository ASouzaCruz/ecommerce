# Diagrama UML - Sistema E-commerce


### Classes Contáveis (Mínimo 11)
✓ **25 classes implementadas:**
1. Produto (abstrata)
2. ProdutoFisico
3. ProdutoDigital
4. Usuario (abstrata)
5. Cliente
6. Administrador
7. Pagamento (abstrata)
8. PagamentoCartao
9. PagamentoPix
10. Pedido
11. ItemPedido
12. Carrinho
13. Endereco
14. UnidadePeso
15. ProdutoRepository
16. UsuarioRepository
17. PedidoRepository
18. PersistenciaService
19. Menu
20. CarrinhoVazioException
21. EstoqueInsuficienteException
22. PedidoInvalidoException
23. ProdutoNaoEncontradoException
24. UsuarioNaoEncontradoException
25. TesteUnidadePeso (classe extra)

### Herança e Polimorfismo (Mínimo 2 situações)
1. **Herança Produto** → calcularFrete(), estaDisponivel(), getTipo()
2. **Herança Usuario** → podeGerenciarProdutos(), podeVisualizarRelatorios(), getPerfil()
3. **Herança Pagamento** → processar(), getDescricao()

### Testes (1 classe - extra)
1. **TesteUnidadePeso** → suite de testes de precisão

### Exceções Personalizadas (5 classes)
✓ EstoqueInsuficienteException
✓ PedidoInvalidoException
✓ CarrinhoVazioException
✓ ProdutoNaoEncontradoException
✓ UsuarioNaoEncontradoException

### Estado Dinâmico
✓ **EstadoPedido** com transições controladas: ABERTO → PAGO → SEPARANDO → ENVIADO → ENTREGUE ou CANCELADO

### Regras de Negócio (Mínimo 5)
1. ✓ Validação de Email
2. ✓ Validação de Peso
3. ✓ Redução de Estoque
4. ✓ Carrinho Não Pode Estar Vazio
5. ✓ Apenas Clientes Podem Fazer Pedidos
6. ✓ Conversão de Unidades de Peso
7. ✓ Máquina de Estados do Pedido
8. ✓ Pagamento Só Registra em Pedidos ABERTOS

---

## Diagrama UML Corrigido

\`\`\`mermaid
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
        +setNome(String) void
        +setPreco(double) void
        +getId() String
        +getNome() String
    }

    class ProdutoFisico {
        -double pesoKg
        -int estoque
        -double TARIFA_KG$
        +calcularFrete() double
        +estaDisponivel() boolean
        +getTipo() String
        +reduzirEstoque(int) void
        +aumentarEstoque(int) void
        +getEstoque() int
    }

    class ProdutoDigital {
        -String linkDownload
        -String formato
        +calcularFrete() double
        +estaDisponivel() boolean
        +getTipo() String
        +getLinkDownload() String
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
        +autenticar(String) boolean
        +getId() String
        +getEmail() String
    }

    class Cliente {
        -String cpf
        -List~Endereco~ enderecos
        +podeGerenciarProdutos() boolean
        +podeVisualizarRelatorios() boolean
        +getPerfil() String
        +adicionarEndereco(Endereco) void
        +removerEndereco(String) void
        +getEnderecoEntrega() Endereco
    }

    class Administrador {
        -String cargo
        -boolean superAdmin
        +podeGerenciarProdutos() boolean
        +podeVisualizarRelatorios() boolean
        +getPerfil() String
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
    }

    class PagamentoCartao {
        -String numeroCartao
        -int parcelas
        -String nomeTitular
        +processar() void
        +getDescricao() String
        +validarCartao() boolean
    }

    class PagamentoPix {
        -String chavePix
        -String codigoPix
        +processar() void
        +getDescricao() String
        -gerarCodigoPix() String
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
        -String id
        -String rua
        -String numero
        -String complemento
        -String cidade
        -String estado
        -String cep
        +getId() String
        +toString() String
    }

    %% ════════════════
    %% ITEM DO PEDIDO
    %% ════════════════
    
    class ItemPedido {
        -String id
        -int quantidade
        -double precoUnitario
        -double freteItem
        +calcularSubtotal() double
        +calcularTotalComFrete() double
        +getId() String
        +getQuantidade() int
    }

    %% ══════════════════════════════════════════════════════════════
    %% CARRINHO
    %% ══════════════════════════════════════════════════════════════
    
    class Carrinho {
        -String id
        -List~ItemPedido~ itens
        +adicionarItem(ItemPedido) void
        +removerItem(String) void
        +calcularTotal() double
        +calcularTotalFrete() double
        +limpar() void
        +estaVazio() boolean
        +getItens() List
    }

    %% ══════════════════════════════════════════════════════════════
    %% PEDIDO
    %% ══════════════════════════════════════════════════════════════
    
    class Pedido {
        -String id
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
        +getEstado() EstadoPedido
    }

    %% ══════════════════════════════════════════════════════════════
    %% REPOSITORIES
    %% ══════════════════════════════════════════════════════════════
    
    class ProdutoRepository {
        -List~Produto~ produtos
        +salvar(Produto) void
        +buscarPorId(String) Optional
        +buscarPorIdOuErro(String) Produto
        +listarTodos() List
        +listarDisponiveis() List
        +remover(String) void
    }

    class UsuarioRepository {
        -List~Usuario~ usuarios
        +salvar(Usuario) void
        +buscarPorEmail(String) Optional
        +buscarPorEmailOuErro(String) Usuario
        +listarTodos() List
        +remover(String) void
    }

    class PedidoRepository {
        -List~Pedido~ pedidos
        +salvar(Pedido) void
        +buscarPorId(String) Optional
        +listarTodos() List
        +listarPorCliente(String) List
    }

    %% ══════════════════════════════════════════════════════════════
    %% SERVIÇO DE PERSISTÊNCIA
    %% ══════════════════════════════════════════════════════════════
    
    class PersistenciaService {
        -String DIR$
        +salvarProdutos(List) void
        +carregarProdutos() List
        +salvarUsuarios(List) void
        +carregarUsuarios() List
        +salvarPedidos(List) void
        +carregarPedidos() List
    }

    %% ══════════════════════════════════════════════════════════════
    %% INTERFACE DE USUÁRIO
    %% ══════════════════════════════════════════════════════════════
    
    class Menu {
        -Scanner sc
        -Usuario usuarioLogado
        -Carrinho carrinho
        -ProdutoRepository produtoRepo
        -UsuarioRepository usuarioRepo
        -PedidoRepository pedidoRepo
        +iniciar() void
        -menuLogin() void
        -menuPrincipal() void
        -finalizarPedido() void
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
\`\`\`

---

## 📋 Sumário de Conformidade

| Requisito | Status | Comentário |
|-----------|--------|-----------|
| **Mínimo 11 Classes** | ✅ **24 CLASSES + 1 extra** | Muito acima do mínimo (excluindo enums) |
| **CRUD Completo** | ✅ **SIM** | 3 repositories com todas operações |
| **Encapsulamento Total** | ✅ **100%** | Todos atributos privados com getters/setters |
| **Polimorfismo** | ✅ **3+ SITUAÇÕES** | Usuario, Produto, Pagamento |
| **Regras de Negócio** | ✅ **8 REGRAS** | Mais que o mínimo de 5 |
| **Interação entre Classes** | ✅ **SIM** | Sistema integrado e complexo |
| **Tratamento de Exceções** | ✅ **SIM** | 5 exceções personalizadas |
| **Estado Dinâmico** | ✅ **SIM** | Máquina de estados em Pedido |
| **Persistência em Arquivos** | ✅ **SIM** | 5 arquivos CSV com I/O |
| **Classes Filhas com Membros** | ✅ **SIM** | Todas filhas têm atributos próprios |
| **Polimorfismo Relevante** | ✅ **SIM** | Afeta regras de negócio |

---

##  Principais Melhorias no Diagrama

1. ✅ **Corrigido:** Separação clara de atributos por classe
2. ✅ **Adicionado:** Todos os getters/setters relevantes
3. ✅ **Clarificado:** Relacionamentos entre Menu, Repositories e Services
4. ✅ **Definido:** Multiplicidades (1, 0..*, 1..*, 0..1)
5. ✅ **Especificado:** Métodos abstratos com asterisco (*)
6. ✅ **Validado:** Todas as exceções lançadas pelas classes apropriadas
