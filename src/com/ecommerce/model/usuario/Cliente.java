package com.ecommerce.model.usuario;

import com.ecommerce.model.Endereco;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String cpf;
    private List<Endereco> enderecos;

    public Cliente(String id, String nome, String email, String senha, String cpf){

        super(id, nome, email, senha);

        this.cpf = cpf;
        this.enderecos = new ArrayList<>();
    }

    @Override
    public boolean podeGerenciarProdutos(){
        return false;
    }

    @Override
    public boolean podeVisualizarRelatorios(){
        return false;
    }

    @Override
    public String getPerfil(){
        return "Cliente";
    }

    public void adicionarEndereco(Endereco endereco){
        enderecos.add(endereco);
    }

    public void removerEndereco(String id){
        enderecos.removeIf(endereco -> endereco.getId().equals(id));
    }

    public Endereco getEnderecoEntrega(){
        if(enderecos.isEmpty()){
            return null;
        }

        return enderecos.get(0);
    }
}
