package com.serasaexperian.api;

import com.serasaexperian.domain.model.Endereco;
import com.serasaexperian.domain.service.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping("/{cep}")
    public Endereco buscaEnderecoPeloCep(@PathVariable String cep) throws Exception {
        return enderecoService.buscarEnderecoPeloCep(cep);
    }
}
