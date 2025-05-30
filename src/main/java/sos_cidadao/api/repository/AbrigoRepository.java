package sos_cidadao.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sos_cidadao.api.model.Abrigo;

@Repository
public interface AbrigoRepository extends JpaRepository<Abrigo, Long>{
    
}
