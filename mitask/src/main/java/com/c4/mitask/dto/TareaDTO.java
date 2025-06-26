package com.c4.mitask.dto;

import java.time.LocalDate;

import com.c4.mitask.model.EstadoTarea;
import com.c4.mitask.model.PrioridadTarea;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TareaDTO {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String descripcion;
    @NotNull(message = "La prioridad es obligatoria")
    private PrioridadTarea prioridad;
    @NotNull(message = "El estado es obligatorio")
    private EstadoTarea estado;
    private LocalDate fechaVencimiento;
    private Long proyectoId;
    private Long usuarioId;
}
