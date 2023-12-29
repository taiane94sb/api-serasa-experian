package com.serasaexperian.config;

import com.serasaexperian.api.model.output.PessoaOutput;
import com.serasaexperian.domain.model.Pessoa;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}
