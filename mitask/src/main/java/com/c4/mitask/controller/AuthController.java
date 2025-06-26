package com.c4.mitask.controller;

import com.c4.mitask.*;
import com.c4.mitask.config.JwtUtil;
import com.c4.mitask.dto.AuthRequest;
import com.c4.mitask.dto.AuthResponse;
import com.c4.mitask.dto.UsuarioRequest;
import com.c4.mitask.model.Rol;
import com.c4.mitask.model.Usuario;
import com.c4.mitask.repository.RolRepository;
import com.c4.mitask.repository.UsuarioRepository;
import com.c4.mitask.service.CustomUserDetailsService;
import com.c4.mitask.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getCorreoElectronico(),
                            authRequest.getPassword()));
        } catch (Exception e) {
            System.out.println("Fallo en autenticación: " + e.getMessage());
            throw new Exception("Credenciales inválidas", e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getCorreoElectronico());
        Usuario usuario = usuarioService.findByCorreoElectronico(authRequest.getCorreoElectronico());
        Integer clientId = null;
        String token = jwtUtil.generateToken(userDetails, usuario, clientId);
        System.out.println("Token generado para: " + authRequest.getCorreoElectronico() + ", Rol: "
                + userDetails.getAuthorities() + ", UserId: " + usuario.getId() + ", ClientId: " + clientId);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody UsuarioRequest usuarioRequest) {
        if (usuarioRequest.getRolId() == null || !usuarioRequest.getRolId().equals(2)) {
            return ResponseEntity.badRequest().body("Solo se permite registrar usuarios con rol Colaborador (ID 2)");
        }

        try {
            userDetailsService.loadUserByUsername(usuarioRequest.getCorreoElectronico());
            return ResponseEntity.badRequest().body("El usuario ya existe");
        } catch (UsernameNotFoundException e) {
            // Continuar con el registro
        }

        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico(usuarioRequest.getCorreoElectronico());
        usuario.setPassword(usuarioRequest.getPassword());
        Rol rol = rolRepository.findById(usuarioRequest.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        usuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok("Registro exitoso. Por favor, inicia sesión.");
    }

    @PostMapping("/admin/usuarios")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico(usuarioRequest.getCorreoElectronico());
        usuario.setPassword(usuarioRequest.getPassword());
        Rol rol = rolRepository.findById(usuarioRequest.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        usuario = usuarioService.registrarUsuario(usuario);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/admin/usuarios")
    public ResponseEntity<List<Usuario>> consultarUsuarios() {
        return ResponseEntity.ok(usuarioService.consultarTodosLosUsuarios());
    }

    @DeleteMapping("/admin/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) { 
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id,
            @RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuarioExistente = usuarioService.findById(id);
        if (usuarioRequest.getCorreoElectronico() != null) {
            if (!usuarioRequest.getCorreoElectronico().equals(usuarioExistente.getCorreoElectronico()) &&
                    usuarioRepository.findByCorreoElectronico(usuarioRequest.getCorreoElectronico()).isPresent()) {
                return ResponseEntity.badRequest().body(null); // Correo ya existe
            }
            usuarioExistente.setCorreoElectronico(usuarioRequest.getCorreoElectronico());
        }
        if (usuarioRequest.getPassword() != null && !usuarioRequest.getPassword().isEmpty()) {
            usuarioExistente.setPassword(usuarioRequest.getPassword());
        }
        if (usuarioRequest.getRolId() != null) {
            Rol rol = rolRepository.findById(usuarioRequest.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            if (rol.getId() == 1 && usuarioExistente.getId() != 1) { // Evitar cambiar a Admin excepto para el admin
                                                                     // original
                return ResponseEntity.badRequest().body(null);
            }
            usuarioExistente.setRol(rol);
        }
        usuarioService.registrarUsuario(usuarioExistente); // Guardar cambios
        return ResponseEntity.ok(usuarioExistente);
    }
}