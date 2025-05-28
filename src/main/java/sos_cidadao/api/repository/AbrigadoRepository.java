package sos_cidadao.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sos_cidadao.api.model.Abrigado;

@Repository
public interface AbrigadoRepository extends JpaRepository<Abrigado, String> {
}
