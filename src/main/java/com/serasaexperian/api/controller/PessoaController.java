package com.serasaexperian.api.controller;

import com.serasaexperian.api.assembler.PessoaAssembler;
import com.serasaexperian.api.model.input.PessoaInput;
import com.serasaexperian.api.model.output.PessoaOutput;
import com.serasaexperian.domain.model.Pessoa;
import com.serasaexperian.domain.repository.PessoaRepository;
import com.serasaexperian.domain.service.EnderecoService;
import com.serasaexperian.domain.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;
    private final EnderecoService enderecoService;
    private final PessoaRepository pessoaRepository;
    private final ModelMapper modelMapper;
    private final PessoaAssembler pessoaAssembler;

    @GetMapping("listarPessoas")
    @Tag(name = "Pessoa", description = "Pessoa")
    @Operation(summary = "Listagem de pessoas cadastradas", description = "Retorna a lista de pessoas cadastradas")
    public List<PessoaOutput> listar() {
        return pessoaAssembler.toCollectionModel(pessoaRepository.findAll());
    }

    @GetMapping("porId/{pessoaId}")
    @Tag(name = "Pessoa", description = "Pessoa")
    @Operation(summary = "Pessoa cadastrada que possui o id fornecido", description = "Retorna a pessoa baseado no id fornecido")
    public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable Long pessoaId) {
        return pessoaRepository.findById(pessoaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("porNome/{pessoaNome}")
    @Tag(name = "Pessoa", description = "Pessoa")
    @Operation(summary = "Pessoa cadastrada que possui o nome fornecido", description = "Retorna a pessoa baseado no nome fornecido")
    public ResponseEntity<Pessoa> buscarPessoaPorNome(@PathVariable String pessoaNome) {
        return pessoaRepository.findByNome(pessoaNome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("porIdade/{pessoaIdade}")
    @Tag(name = "Pessoa", description = "Pessoa")
    @Operation(summary = "Pessoa cadastrada que possui a idade fornecida", description = "Retorna a pessoa baseado na idade fornecida")
    public ResponseEntity<Pessoa> buscarPessoaPorIdade(@PathVariable Integer pessoaIdade) {
        return pessoaRepository.findByIdade(pessoaIdade)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name = "Pessoa", description = "Pessoa")
    @Operation(summary = "Cadastra uma pessoa com as informações fornecidas", description = "Cadastra uma pessoa baseadas nas informações fornecidas")
    public PessoaOutput cadastrar(@RequestBody PessoaInput pessoaInput) {
        Pessoa novaPessoa = pessoaAssembler.toEntity(pessoaInput);
        Pessoa pessoaCadastrada = null;
        try {
            pessoaCadastrada = pessoaService.cadastrar(novaPessoa, pessoaInput.getCep());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pessoaAssembler.toModel(pessoaCadastrada);
    }

    @DeleteMapping("/{pessoaId}")
    @Tag(name = "Pessoa", description = "Pessoa")
    @Operation(summary = "Remove uma pessoa que possui o id fornecido", description = "Remove uma pessoa baseado no id fornecido")
    public ResponseEntity<Pessoa> deletar(@PathVariable Long pessoaId) {
        if(!pessoaRepository.existsById(pessoaId)) {
            return ResponseEntity.notFound().build();
        }

        pessoaService.deletar(pessoaId);
        return ResponseEntity.noContent().build();
    }
}
