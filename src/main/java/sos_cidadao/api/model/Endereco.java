package sos_cidadao.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enderecos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endereco_id")
    private Long id;

    @NotBlank(message = "Rua não pode ser nulo")
    @Column(name = "rua", nullable = false)
    private String rua;
    
    @NotBlank(message = "Cidade não pode ser nulo")
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "numero", nullable = false)
    private int numero;

    @NotBlank(message = "Estado não pode ser nulo")
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotBlank(message = "CEP não pode ser nulo")
    @Column(name = "cep", nullable = false)
    private String cep;

    @NotBlank(message = "Pais não pode ser nulo")
    @Column(name = "pais", nullable = false)
    private String pais;

    @NotBlank(message = "Bairro não pode ser nulo")
    private String bairro;
}
