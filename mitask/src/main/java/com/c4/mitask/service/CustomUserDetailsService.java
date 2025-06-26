package com.c4.mitask.service;


import com.c4.mitask.model.Usuario;
import com.c4.mitask.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correoElectronico));
        System.out.println("Cargando usuario: " + correoElectronico + ", Rol: " + usuario.getRol().getNombre());
        return User.builder()
            .username(usuario.getCorreoElectronico())
            .password(usuario.getPassword())
            .roles(usuario.getRol().getNombre()) // Solo el nombre del rol, sin prefijo
            .build();
    }
}
