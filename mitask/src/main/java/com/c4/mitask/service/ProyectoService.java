package com.c4.mitask.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.c4.mitask.dto.ProyectoDTO;
import com.c4.mitask.model.Proyecto;
import com.c4.mitask.model.Usuario;
import com.c4.mitask.repository.ProyectoRepository;
import com.c4.mitask.repository.UsuarioRepository;

@Service
public class ProyectoService {
    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProyectoService(ProyectoRepository proyectoRepository, UsuarioRepository usuarioRepository) {
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ProyectoDTO crearProyecto(ProyectoDTO dto) {
        Proyecto proyecto = new Proyecto();
        mapearAEntidad(dto, proyecto);
        proyecto = proyectoRepository.save(proyecto);
        return mapearADTO(proyecto);
    }

    public ProyectoDTO actualizarProyecto(Long id, ProyectoDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        mapearAEntidad(dto, proyecto);
        proyecto = proyectoRepository.save(proyecto);
        return mapearADTO(proyecto);
    }

    public ProyectoDTO obtenerProyecto(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        return mapearADTO(proyecto);
    }

    public List<Proyecto> obtenerTodosProyectos() {
        return proyectoRepository.findAll();
    }

    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }

    private ProyectoDTO mapearADTO(Proyecto proyecto) {
        ProyectoDTO dto = new ProyectoDTO();
        dto.setId(proyecto.getId());
        dto.setNombre(proyecto.getNombre());
        dto.setDescripcion(proyecto.getDescripcion());
        dto.setFechaInicio(proyecto.getFechaInicio());
        dto.setFechaFin(proyecto.getFechaFin());
        dto.setEstado(proyecto.getEstado());
        dto.setUsuarioIds(proyecto.getUsuarios().stream().map(Usuario::getId).collect(Collectors.toSet()));
        return dto;
    }

    private void mapearAEntidad(ProyectoDTO dto, Proyecto proyecto) {
        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setFechaInicio(dto.getFechaInicio());
        proyecto.setFechaFin(dto.getFechaFin());
        proyecto.setEstado(dto.getEstado());
        if (dto.getUsuarioIds() != null) {
            proyecto.setUsuarios(dto.getUsuarioIds().stream()
                    .map(id -> usuarioRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado")))
                    .collect(Collectors.toSet()));
        }
    }
}

