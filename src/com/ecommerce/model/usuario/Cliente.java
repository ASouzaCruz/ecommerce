package com.ecommerce.model.usuario;

import com.ecommerce.model.Endereco;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String cpf;
    private List<Endereco> enderecos;

    public Cliente(String nome, String email, String senha, String cpf) {
        super(nome, email, senha);
        if (cpf == null || cpf.replaceAll("[^0-9]", "").length() != 11)
            throw new IllegalArgumentException("CPF invalido. Deve conter 11 digitos.");
        this.cpf = cpf.replaceAll("[^0-9]", "");
        this.enderecos = new ArrayList<>();
    }

    @Override
    public boolean podeGerenciarProdutos() { return false; }

    @Override
    public boolean podeVisualizarRelatorios() { return false; }

    @Override
    public String getPerfil() { return "CLIENTE"; }

    public void adicionarEndereco(Endereco endereco) {
        if (endereco == null) throw new IllegalArgumentException("Endereco invalido.");
        this.enderecos.add(endereco);
    }

    public void removerEndereco(int index) {
        if (index < 0 || index >= enderecos.size())
            throw new IndexOutOfBoundsException("Endereco nao encontrado.");
        enderecos.remove(index);
    }

    public String getCpf() { return cpf; }
    public List<Endereco> getEnderecos() { return new ArrayList<>(enderecos); }

    public Endereco getEnderecoEntrega() {
        if (enderecos.isEmpty()) throw new IllegalStateException("Cliente nao possui endereco cadastrado.");
        return enderecos.get(0);
    }
}
