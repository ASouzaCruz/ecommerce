package com.ecommerce.repository;

import com.ecommerce.model.usuario.Usuario;
import java.util.List;
import java.util.Optional;

// Implementação inicial baseada no diagrama UML.
// Algumas funcionalidades possuem apenas a estrutura básica e serão implementadas posteriormente.

public class UsuarioRepository {

    private List<Usuario> usuarios;

    public void salvar(String id){

    }

    public Optional<Usuario> buscarPorEmail(){
        return null;
    }

    public Usuario buscarPorEmailOuErro(){
        return null;
    }

    public List<Usuario> listarTodos(){
        return usuarios;
    }

    public void remover(){

    }

}
