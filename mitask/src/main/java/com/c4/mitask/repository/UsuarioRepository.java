package com.c4.mitask.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.c4.mitask.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

    Optional<Usuario> findById(Integer Long);

}
