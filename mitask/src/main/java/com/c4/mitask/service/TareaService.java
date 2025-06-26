package com.c4.mitask.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.c4.mitask.dto.TareaDTO;
import com.c4.mitask.model.EstadoTarea;
import com.c4.mitask.model.PrioridadTarea;
import com.c4.mitask.model.Proyecto;
import com.c4.mitask.model.Tarea;
import com.c4.mitask.model.Usuario;
import com.c4.mitask.repository.ProyectoRepository;
import com.c4.mitask.repository.TareaRepository;
import com.c4.mitask.repository.UsuarioRepository;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    public TareaService(TareaRepository tareaRepository, ProyectoRepository proyectoRepository, UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public TareaDTO crearTarea(TareaDTO dto) {
        Tarea tarea = new Tarea();
        mapearAEntidad(dto, tarea);
        tarea = tareaRepository.save(tarea);
        return mapearADTO(tarea);
    }

    public TareaDTO actualizarTarea(Long id, TareaDTO dto) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        mapearAEntidad(dto, tarea);
        tarea = tareaRepository.save(tarea);
        return mapearADTO(tarea);
    }

    public TareaDTO obtenerTarea(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        return mapearADTO(tarea);
    }

    public List<TareaDTO> obtenerTodasTareas() {
        return tareaRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }

    public List<TareaDTO> obtenerTareasPorProyecto(Long proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public List<TareaDTO> obtenerTareasPorUsuario(Long usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public List<TareaDTO> obtenerTareasPorEstado(String estado) {
        return tareaRepository.findByEstado(EstadoTarea.valueOf(estado.toUpperCase())).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public List<TareaDTO> obtenerTareasPorPrioridad(String prioridad) {
        return tareaRepository.findByPrioridad(PrioridadTarea.valueOf(prioridad.toUpperCase())).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    private TareaDTO mapearADTO(Tarea tarea) {
        TareaDTO dto = new TareaDTO();
        dto.setId(tarea.getId());
        dto.setNombre(tarea.getNombre());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setPrioridad(tarea.getPrioridad());
        dto.setEstado(tarea.getEstado());
        dto.setFechaVencimiento(tarea.getFechaVencimiento());
        dto.setProyectoId(tarea.getProyecto() != null ? tarea.getProyecto().getId() : null);
        dto.setUsuarioId(tarea.getUsuario() != null ? tarea.getUsuario().getId() : null);
        return dto;
    }

    private void mapearAEntidad(TareaDTO dto, Tarea tarea) {
        tarea.setNombre(dto.getNombre());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setPrioridad(dto.getPrioridad());
        tarea.setEstado(dto.getEstado());
        tarea.setFechaVencimiento(dto.getFechaVencimiento());
        if (dto.getProyectoId() != null) {
            Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId())
                    .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
            tarea.setProyecto(proyecto);
        }
        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            tarea.setUsuario(usuario);
        }
    }
}