package sos_cidadao.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sos_cidadao.api.dto.UsuarioRequestDTO;
import sos_cidadao.api.dto.UsuarioResponseDTO;
import sos_cidadao.api.model.Abrigado;
import sos_cidadao.api.model.Usuario;
import sos_cidadao.api.model.Voluntario;
import sos_cidadao.api.model.enums.TipoUsuario;
import sos_cidadao.api.repository.AbrigadoRepository;
import sos_cidadao.api.repository.UsuarioRepository;
import sos_cidadao.api.repository.VoluntarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AbrigadoRepository abrigadoRepository;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRequestDTO == null) {
            throw new IllegalArgumentException("O objeto UsuarioRequestDTO não pode ser nulo");
        }

        if (usuarioRequestDTO.email() == null || usuarioRequestDTO.email().isBlank()) {
            throw new IllegalArgumentException("O campo email não pode ser nulo ou vazio");
        }

        if (usuarioRequestDTO.senha() == null || usuarioRequestDTO.senha().isBlank()) {
            throw new IllegalArgumentException("O campo senha não pode ser nulo ou vazio");
        }

        if (!usuarioRequestDTO.senha().equals(usuarioRequestDTO.confirmarSenha())) {
            throw new IllegalArgumentException("As senhas não coincidem");
        }

        try {
            Usuario usuario = Usuario.builder()
                .nome(usuarioRequestDTO.nome())
                .email(usuarioRequestDTO.email())
                .senha(passwordEncoder.encode(usuarioRequestDTO.senha()))
                .tipo(usuarioRequestDTO.tipo())
                .build();

            usuarioRepository.save(usuario);

            if (usuarioRequestDTO.tipo() == TipoUsuario.ABRIGADO) {
                Abrigado abrigado = new Abrigado();
                abrigado.setUsuario(usuario);
                abrigado.setNecessidadesEspecificas(usuarioRequestDTO.necessidadesEspecificas());
                abrigadoRepository.save(abrigado);
            } else if (usuarioRequestDTO.tipo() == TipoUsuario.VOLUNTARIO) {
                Voluntario voluntario = new Voluntario();
                voluntario.setUsuario(usuario);
                voluntario.setHabilidades(usuarioRequestDTO.habilidades());
                voluntario.setDisponivel(true);
                voluntarioRepository.save(voluntario);
            }

            var usuarioSalvo = usuarioRepository.save(usuario);
            return new UsuarioResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getRole()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar o usuário: " + e.getMessage(), e);
        }
    }

}
