package com.serasaexperian.api.assembler;

import com.serasaexperian.api.model.input.PessoaInput;
import com.serasaexperian.api.model.output.PessoaOutput;
import com.serasaexperian.domain.model.Pessoa;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class PessoaAssembler {

    private final ModelMapper modelMapper;

    public Pessoa toEntity(PessoaInput pessoaInput) {
        return modelMapper.map(pessoaInput, Pessoa.class);
    }

    public PessoaOutput toModel(Pessoa pessoa) {
        return modelMapper.map(pessoa, PessoaOutput.class);
    }

    public List<PessoaOutput> toCollectionModel(List<Pessoa> pessoas) {
        return pessoas.stream()
                .map(this::toModel)
                .toList();
    }
}
