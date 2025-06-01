package sos_cidadao.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sos_cidadao.api.model.Usuario;
import sos_cidadao.api.model.enums.TipoUsuario;
import sos_cidadao.api.model.enums.UserRole;
import sos_cidadao.api.repository.UsuarioRepository;

@Component
public class DatabaseSeeder {
    
    @Autowired UsuarioRepository usuarioRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        var usuarios = List.of(
            Usuario.builder()
                .nome("Samuel")
                .email("samuel@gmail.com")
                .senha(passwordEncoder.encode("123"))
                .tipo(TipoUsuario.VOLUNTARIO)
                .role(UserRole.ADMIN)
                .build(),

            Usuario.builder()
                .nome("Lopes")
                .email("lopes@gmail.com")
                .senha(passwordEncoder.encode("123"))
                .tipo(TipoUsuario.VOLUNTARIO)
                .role(UserRole.ADMIN)
                .build(),

            Usuario.builder()
                .nome("Matheus")
                .email("matheus@gmail.com")
                .senha(passwordEncoder.encode("123"))
                .tipo(TipoUsuario.VOLUNTARIO)
                .role(UserRole.ADMIN)
                .build()
        );
        usuarioRepository.saveAll(usuarios);
    }

}
