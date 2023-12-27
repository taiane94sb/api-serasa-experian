package com.serasaexperian.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50)
    private String nome;

    @NotNull(message = "Idade é obrigatório")
    private Integer idade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    @JsonManagedReference
    private Endereco endereco;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 11)
    private String telefone;

    @NotNull(message = "Score é obrigatório")
    @Min(0)
    @Max(100)
    private Integer score;
}
