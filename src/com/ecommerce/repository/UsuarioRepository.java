package com.ecommerce.repository;

import com.ecommerce.exception.UsuarioNaoEncontradoException;
import com.ecommerce.model.usuario.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository {
    private List<Usuario> usuarios = new ArrayList<>();

    public void salvar(Usuario usuario) {
        if (buscarPorEmail(usuario.getEmail()).isPresent())
            throw new IllegalStateException("Email ja cadastrado: " + usuario.getEmail());
        usuarios.add(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarios.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public Usuario buscarPorEmailOuErro(String email) {
        return buscarPorEmail(email).orElseThrow(() -> new UsuarioNaoEncontradoException(email));
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public List<Usuario> listarTodos() { return new ArrayList<>(usuarios); }

    public void remover(String id) {
        Usuario u = usuarios.stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        usuarios.remove(u);
    }

    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
}