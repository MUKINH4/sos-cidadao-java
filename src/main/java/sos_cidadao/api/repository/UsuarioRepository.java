package sos_cidadao.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sos_cidadao.api.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>, JpaSpecificationExecutor<String> {
    Usuario findByEmail(String username);
}
