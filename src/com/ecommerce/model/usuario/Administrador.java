package com.ecommerce.model.usuario;

public class Administrador extends Usuario {
    private String cargo;
    private boolean superAdmin;

    public Administrador(String nome, String email, String senha, String cargo, boolean superAdmin) {
        super(nome, email, senha);
        if (cargo == null || cargo.isBlank()) throw new IllegalArgumentException("Cargo nao pode ser vazio.");
        this.cargo = cargo;
        this.superAdmin = superAdmin;
    }

    @Override
    public boolean podeGerenciarProdutos() { return true; }

    @Override
    public boolean podeVisualizarRelatorios() { return true; }

    @Override
    public String getPerfil() { return superAdmin ? "SUPER_ADMIN" : "ADMIN"; }

    public String getCargo() { return cargo; }
    public boolean isSuperAdmin() { return superAdmin; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public boolean podeRemoverAdmins() { return superAdmin; }
}
