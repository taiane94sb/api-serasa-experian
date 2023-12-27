package com.serasaexperian.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Cep é obrigatório")
    private String cep;

    @NotBlank(message = "Estado é obrigatório")
    @Column(name = "estado")
    private String uf;

    @NotBlank(message = "Cidade é obrigatório")
    @Column(name = "cidade")
    private String localidade;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    @OneToOne(mappedBy = "endereco")
    @JsonBackReference
    private Pessoa pessoa;
}
