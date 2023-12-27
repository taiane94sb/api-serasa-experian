package com.serasaexperian.api.model.output;

import com.serasaexperian.domain.model.Pessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoOutput {

    private Long id;

    private String cep;

    private String uf;

    private String localidade;

    private String bairro;

    private String logradouro;

    private Pessoa pessoa;
}
