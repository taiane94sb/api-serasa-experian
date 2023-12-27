package com.serasaexperian.api.model.output;


import com.serasaexperian.domain.model.Endereco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaOutput {
    private Long id;

    private String nome;

    private Integer idade;

    private Endereco endereco;

    private String telefone;

    private Integer score;
}
