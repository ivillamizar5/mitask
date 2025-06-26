package com.c4.mitask.dto;

import java.time.LocalDate;
import java.util.Set;

import com.c4.mitask.model.EstadoProyecto;

import lombok.Data;

@Data
public class ProyectoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoProyecto estado;
    private Set<Long> usuarioIds;
}
