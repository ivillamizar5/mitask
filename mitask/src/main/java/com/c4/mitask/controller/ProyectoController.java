package com.c4.mitask.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.c4.mitask.dto.ProyectoDTO;
import com.c4.mitask.service.ProyectoService;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {
    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @Operation(summary = "Crear un nuevo proyecto")
    @PostMapping
    public ResponseEntity<ProyectoDTO> crearProyecto(@RequestBody ProyectoDTO proyectoDTO) {
        return ResponseEntity.ok(proyectoService.crearProyecto(proyectoDTO));
    }

    @Operation(summary = "Actualizar un proyecto existente")
    @PatchMapping("/{id}")
    public ResponseEntity<ProyectoDTO> actualizarProyecto(@PathVariable Long id, @RequestBody ProyectoDTO proyectoDTO) {
        return ResponseEntity.ok(proyectoService.actualizarProyecto(id, proyectoDTO));
    }

    @Operation(summary = "Obtener un proyecto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> obtenerProyecto(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.obtenerProyecto(id));
    }

    @Operation(summary = "Obtener todos los proyectos")
    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> obtenerTodosProyectos() {
        return ResponseEntity.ok(proyectoService.obtenerTodosProyectos());
    }

    @Operation(summary = "Eliminar un proyecto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }
}

