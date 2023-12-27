package com.serasaexperian.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaInput {

    private String nome;

    private Integer idade;

    private String cep;

    private String telefone;

    private Integer score;
}
