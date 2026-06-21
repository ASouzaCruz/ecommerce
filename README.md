# E-Commerce Java

Sistema de e-commerce desenvolvido em **Java**, utilizando conceitos de **Programação Orientada a Objetos (POO)** e organização em camadas.

O projeto simula uma loja virtual, permitindo o gerenciamento de usuários, produtos, carrinho de compras, pedidos e pagamentos através de uma interface de terminal.

---

# Estrutura do projeto

O projeto está organizado seguindo uma divisão por responsabilidades:

```
src
└── com
    └── ecommerce
        ├── Main.java
        │
        ├── exception
        │   ├── CarrinhoVazioException.java
        │   ├── EstoqueInsuficienteException.java
        │   ├── PedidoInvalidoException.java
        │   ├── ProdutoNaoEncontradoException.java
        │   └── UsuarioNaoEncontradoException.java
        │
        ├── model
        │   ├── pagamento
        │   │
        │   ├── produto
        │   │   ├── Produto.java
        │   │   ├── ProdutoDigital.java
        │   │   └── ProdutoFisico.java
        │   │
        │   └── usuario
        │       ├── Usuario.java
        │       ├── Cliente.java
        │       └── Administrador.java
        │
        ├── repository
        │   ├── UsuarioRepository.java
        │   ├── ProdutoRepository.java
        │   └── PedidoRepository.java
        │
        ├── service
        │   └── PersistenciaService.java
        │
        ├── test
        │   └── TesteUnidadePeso.java
        │
        └── ui
            └── Menu.java
```

---

# Como executar o projeto

## 1. Pré-requisitos

Antes de executar o projeto, certifique-se de possuir:

- **Java JDK 17 ou superior instalado**

Para verificar a instalação:

```bash
java -version
```

---

# 2. Clonar o repositório ou abra o arquivo zipado na sua IDE.

Clone o projeto utilizando:

```bash
git clone https://github.com/ASouzaCruz/ecommerce.git
```
Arquivo zipado:
``` 
[Link do download do projeto zipado](https://github.com/user-attachments/files/29183110/ecommerce-master.zip)

```

Acesse a pasta do projeto:

```bash
cd ecommerce
```

---

# 3. Compilar o projeto

Entre na pasta onde está localizado o código-fonte:

```bash
cd src
```

Compile todos os arquivos Java:

```bash
javac com/ecommerce/**/*.java
```

---

# 4. Executar o sistema

A classe principal do projeto está localizada em:

```
src/com/ecommerce/Main.java
```

Execute utilizando:

```bash
java com.ecommerce.Main
```

Após a execução, o sistema iniciará o menu interativo no terminal.

---

# Funcionalidades

O sistema permite:

- Cadastro e gerenciamento de usuários
- Diferenciação entre clientes e administradores
- Cadastro e gerenciamento de produtos
- Controle de estoque
- Adição de produtos ao carrinho
- Criação e gerenciamento de pedidos
- Processamento de pagamentos
- Tratamento de erros através de exceções personalizadas

---

# Organização das camadas

## Model

Responsável por representar as entidades do sistema.

Principais classes:

### Usuários

- `Usuario`
- `Cliente`
- `Administrador`

### Produtos

- `Produto`
- `ProdutoFisico`
- `ProdutoDigital`

### Pedidos

- `Pedido`
- `ItemPedido`
- `EstadoPedido`

### Outros modelos

- `Carrinho`
- `Endereco`
- `UnidadePeso`

---

## Repository

Responsável pelo armazenamento e gerenciamento dos dados:

- `UsuarioRepository`
- `ProdutoRepository`
- `PedidoRepository`

---

## Service

Responsável por funcionalidades auxiliares e regras de persistência.

Classe principal:

- `PersistenciaService`

Responsabilidades:

- Criar a pasta de armazenamento
- Salvar dados em arquivos CSV
- Ler informações persistidas
- Restaurar dados ao iniciar o sistema


---

## UI

Responsável pela interação com o usuário através do terminal:

- `Menu.java`

---

## Exception

Contém exceções específicas criadas para controlar situações de erro:

- `CarrinhoVazioException`
- `EstoqueInsuficienteException`
- `PedidoInvalidoException`
- `ProdutoNaoEncontradoException`
- `UsuarioNaoEncontradoException`

---

# Conceitos de Programação Orientada a Objetos aplicados

Durante o desenvolvimento foram utilizados:

✔ Encapsulamento  
✔ Herança  
✔ Polimorfismo  
✔ Abstração  
✔ Classes e objetos  
✔ Modificadores de acesso (`private`, `public`, `protected`)  
✔ Sobrescrita de métodos (`@Override`)  
✔ Tratamento de exceções  

---

# Teste

O teste esta localizado no pacote:

```
com.ecommerce.test
```

Classe Teste auxiliar:

```
TesteUnidadePeso.java
```

---

# Diagrama UML

O projeto possui um diagrama UML representando a estrutura das classes:

```
DIAGRAMA_UML_CORRIGIDO.md
```

---

# Desenvolvimento

Projeto desenvolvido com o objetivo de aplicar conceitos de Java e Programação Orientada a Objetos na construção de um sistema de e-commerce.

---

# Licença

Este projeto possui finalidade acadêmica e educacional.
