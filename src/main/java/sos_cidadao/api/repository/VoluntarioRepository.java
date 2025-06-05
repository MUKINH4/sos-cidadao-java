package sos_cidadao.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sos_cidadao.api.model.Voluntario;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, String>, JpaSpecificationExecutor<String> {
    Voluntario findByUsuarioId(String usuarioId);
}
