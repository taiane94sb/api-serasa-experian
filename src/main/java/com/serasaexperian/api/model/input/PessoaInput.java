package com.serasaexperian.api.model.input;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaInput {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50)
    private String nome;

    @NotNull(message = "Idade é obrigatório")
    private Integer idade;

    @NotBlank(message = "Cep é obrigatório")
    private String cep;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 11)
    private String telefone;

    @NotNull(message = "Score é obrigatório")
    @Min(0)
    @Max(1000)
    private Integer score;
}
