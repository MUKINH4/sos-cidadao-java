package sos_cidadao.api.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (usuarioRequestDTO.nome() == null || usuarioRequestDTO.nome().isBlank()) {
            throw new IllegalArgumentException("O campo nome não pode ser nulo ou vazio");
        }

        if (usuarioRequestDTO.email() == null || usuarioRequestDTO.email().isBlank()) {
            throw new IllegalArgumentException("O campo email não pode ser nulo ou vazio");
        }

        if (usuarioRequestDTO.senha() == null || usuarioRequestDTO.senha().isBlank()) {
            throw new IllegalArgumentException("O campo senha não pode ser nulo ou vazio");
        }

        if (usuarioRequestDTO.confirmarSenha() == null || usuarioRequestDTO.confirmarSenha().isBlank()) {
            throw new IllegalArgumentException("O campo confirmar senha não pode ser nulo ou vazio");
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
                abrigadoRepository.save(abrigado);
            } else if (usuarioRequestDTO.tipo() == TipoUsuario.VOLUNTARIO) {
                Voluntario voluntario = new Voluntario();
                voluntario.setUsuario(usuario);
                voluntario.setDisponivel(true);
                voluntarioRepository.save(voluntario);
            }

            var usuarioSalvo = usuarioRepository.save(usuario);
            return new UsuarioResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getRole()
            );
        } catch (DataIntegrityViolationException e) {
            Throwable cause = e.getMostSpecificCause();
            if (cause instanceof ConstraintViolationException) {
                throw (ConstraintViolationException) cause;
            }
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar o usuário: " + e.getMessage(), e);
        }
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> buscarPorId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo ou vazio");
        }
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario não encontrado");
        }
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario atualizarUsuario(String id, Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        usuario.setRole(usuarioAtualizado.getRole());
        usuario.setTipo(usuarioAtualizado.getTipo());
        System.out.println("Atualizando usuário: " + usuario);
        System.out.println(usuarioAtualizado.getSenha());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletarUsuario(String id) {
        Voluntario voluntario = voluntarioRepository.findByUsuarioId(id);
        if (voluntario != null) {
            voluntarioRepository.delete(voluntario);
        }
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
