package com.c4.mitask.dto;

import java.time.LocalDate;

import com.c4.mitask.model.EstadoTarea;
import com.c4.mitask.model.PrioridadTarea;

import lombok.Data;

@Data
public class TareaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private PrioridadTarea prioridad;
    private EstadoTarea estado;
    private LocalDate fechaVencimiento;
    private Long proyectoId;
    private Long usuarioId;
}
