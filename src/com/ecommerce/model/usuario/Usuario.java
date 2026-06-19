package com.ecommerce.model.usuario;

import java.util.UUID;

public abstract class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(String nome, String email, String senha) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome nao pode ser vazio.");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Email invalido.");
        if (senha == null || senha.length() < 6) throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres.");
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Polimorfismo 2 - cada tipo de usuario tem permissoes diferentes
    public abstract boolean podeGerenciarProdutos();
    public abstract boolean podeVisualizarRelatorios();
    public abstract String getPerfil();

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public void setId(String id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean autenticar(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s <%s>", getPerfil(), nome, email);
    }
}
