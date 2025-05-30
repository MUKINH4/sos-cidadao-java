package sos_cidadao.api.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import sos_cidadao.api.controller.VoluntarioController.VoluntarioFilter;
import sos_cidadao.api.model.Voluntario;

public class VoluntarioSpecification {
    public Specification<Voluntario> withFilters(VoluntarioFilter filters){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.nome() != null && !filters.nome().isBlank()){
                predicates.add(
                    cb.like(cb.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%")
                );
            }

            Predicate[] arrayPredicates = predicates.toArray(Predicate[]::new);
            return cb.and(arrayPredicates);
        };
    }
}
