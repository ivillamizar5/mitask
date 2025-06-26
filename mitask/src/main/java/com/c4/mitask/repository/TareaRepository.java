package com.c4.mitask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.c4.mitask.model.EstadoTarea;
import com.c4.mitask.model.PrioridadTarea;
import com.c4.mitask.model.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Long>{
    List<Tarea> findByProyectoId(Long proyectoId);
    List<Tarea> findByUsuarioId(Long usuarioId);
    List<Tarea> findByEstado(EstadoTarea estado);
    List<Tarea> findByPrioridad(PrioridadTarea prioridad);
}
