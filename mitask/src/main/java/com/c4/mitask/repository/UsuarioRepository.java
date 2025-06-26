package com.c4.mitask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.c4.mitask.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
