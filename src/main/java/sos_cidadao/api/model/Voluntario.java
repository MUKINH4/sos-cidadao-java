package sos_cidadao.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Voluntario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String habilidades;
    private boolean disponivel;

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
