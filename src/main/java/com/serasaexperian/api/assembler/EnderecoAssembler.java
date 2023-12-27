package com.serasaexperian.api.assembler;


import com.serasaexperian.api.model.output.EnderecoOutput;
import com.serasaexperian.domain.model.Endereco;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class EnderecoAssembler {
    private final ModelMapper modelMapper;

    public EnderecoOutput toModel(Endereco endereco) {
        return modelMapper.map(endereco, EnderecoOutput.class);
    }

    public List<EnderecoOutput> toCollectionModel(List<Endereco> enderecos) {
        return enderecos.stream()
                .map(this::toModel)
                .toList();
    }
}
