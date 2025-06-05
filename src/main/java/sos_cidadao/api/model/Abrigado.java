package sos_cidadao.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Abrigado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "abrigo_id", nullable = true)
    @JsonBackReference
    private Abrigo abrigo;

    @NotBlank(message = "O campo necessidades específicas não pode ser nulo")
    private String necessidadesEspecificas;

    @Min(value = 0, message = "A idade não pode ser negativa")
    @Positive(message = "A idade deve ser um número positivo")
    private int idade;

    @NotBlank(message = "O campo sexo não pode ser nulo")
    private String sexo;

    @NotBlank(message = "O campo nome não pode ser nulo")
    private String nome;
}
