package com.ecommerce.model.usuario;

public abstract class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(String id, String nome, String email, String senha){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public abstract boolean podeGerenciarProdutos();

    public abstract boolean podeVisualizarRelatorios();

    public abstract String getPerfil();

    public String getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }

    public boolean autenticar(String senhaDigitada){
        return this.senha.equals(senhaDigitada);
    }

}
