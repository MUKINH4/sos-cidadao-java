package sos_cidadao.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sos_cidadao.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findUserByEmail(String email);
}
