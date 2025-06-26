package com.c4.mitask.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.c4.mitask.dto.TareaDTO;
import com.c4.mitask.service.TareaService;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @Operation(summary = "Crear una nueva tarea")
    @PostMapping
    public ResponseEntity<TareaDTO> crearTarea(@RequestBody TareaDTO tareaDTO) {
        return ResponseEntity.ok(tareaService.crearTarea(tareaDTO));
    }

    @Operation(summary = "Actualizar una tarea existente")
    @PatchMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizarTarea(@PathVariable Long id, @RequestBody TareaDTO tareaDTO) {
        return ResponseEntity.ok(tareaService.actualizarTarea(id, tareaDTO));
    }

    @Operation(summary = "Obtener una tarea por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> obtenerTarea(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.obtenerTarea(id));
    }

    @Operation(summary = "Obtener todas las tareas")
    @GetMapping
    public ResponseEntity<List<TareaDTO>> obtenerTodasTareas() {
        return ResponseEntity.ok(tareaService.obtenerTodasTareas());
    }

    @Operation(summary = "Eliminar una tarea")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener tareas por proyecto")
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<TareaDTO>> obtenerTareasPorProyecto(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(tareaService.obtenerTareasPorProyecto(proyectoId));
    }

    @Operation(summary = "Obtener tareas por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TareaDTO>> obtenerTareasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(tareaService.obtenerTareasPorUsuario(usuarioId));
    }

    @Operation(summary = "Filtrar tareas por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TareaDTO>> obtenerTareasPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(tareaService.obtenerTareasPorEstado(estado));
    }

    @Operation(summary = "Filtrar tareas por prioridad")
    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<TareaDTO>> obtenerTareasPorPrioridad(@PathVariable String prioridad) {
        return ResponseEntity.ok(tareaService.obtenerTareasPorPrioridad(prioridad));
    }
}

