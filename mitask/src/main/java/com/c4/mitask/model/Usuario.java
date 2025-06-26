package com.c4.mitask.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String correoElectronico;

    @Column(nullable = false, unique = true)
    private String password;

    @ManyToMany(mappedBy = "usuarios")
    private Set<Proyecto> proyectos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
}