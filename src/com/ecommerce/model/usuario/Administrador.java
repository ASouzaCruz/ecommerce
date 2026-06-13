package com.ecommerce.model.usuario;

public class Administrador extends Usuario {
    private String cargo;
    private boolean superAdmin;

    public Administrador(String id, String nome, String email, String senha, String cargo){

        super(id,nome,email,senha);
        
        this.cargo =cargo;
        this.superAdmin = true;
    }

    @Override
    public boolean podeGerenciarProdutos(){
        return true;
    }

    @Override
    public boolean podeVisualizarRelatorios(){
        return true;
    }

    @Override
    public String getPerfil(){
        return "Administrador";
    }

    public boolean podeRemoverAdmins(){
        return true;
    }
}
