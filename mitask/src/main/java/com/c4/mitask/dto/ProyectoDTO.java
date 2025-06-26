package com.c4.mitask.dto;

import java.time.LocalDate;
import java.util.Set;

import com.c4.mitask.model.EstadoProyecto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProyectoDTO {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    @NotNull(message = "El estado es obligatorio")
    private EstadoProyecto estado;
    private Set<Long> usuarioIds;
}
