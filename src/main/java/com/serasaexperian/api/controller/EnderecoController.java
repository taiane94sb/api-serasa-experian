package com.serasaexperian.api.controller;

import com.serasaexperian.domain.model.Endereco;
import com.serasaexperian.domain.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/enderecos")
@Tag(name = "Endereço", description = "Endereço")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping("/{cep}")
    @Operation(summary = "Endereço que possui o cep fornecido", description = "Retorna as informações do endereço baseado no cep fornecido")
    public Endereco buscaEnderecoPeloCep(@PathVariable String cep) throws Exception {
        return enderecoService.buscarEnderecoPeloCep(cep);
    }
}
