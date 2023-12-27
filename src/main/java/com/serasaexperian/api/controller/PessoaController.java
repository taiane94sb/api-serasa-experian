package com.serasaexperian.api.controller;

import com.serasaexperian.api.assembler.PessoaAssembler;
import com.serasaexperian.api.model.input.PessoaInput;
import com.serasaexperian.api.model.output.PessoaOutput;
import com.serasaexperian.domain.model.Pessoa;
import com.serasaexperian.domain.repository.PessoaRepository;
import com.serasaexperian.domain.service.EnderecoService;
import com.serasaexperian.domain.service.PessoaService;
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
    public List<PessoaOutput> listar() {
        return pessoaAssembler.toCollectionModel(pessoaRepository.findAll());
    }

    @GetMapping("porId/{pessoaId}")
    public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable Long pessoaId) {
        return pessoaRepository.findById(pessoaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("porNome/{pessoaNome}")
    public ResponseEntity<Pessoa> buscarPessoaPorNome(@PathVariable String pessoaNome) {
        return pessoaRepository.findByNome(pessoaNome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("porIdade/{pessoaIdade}")
    public ResponseEntity<Pessoa> buscarPessoaPorIdade(@PathVariable Integer pessoaIdade) {
        return pessoaRepository.findByIdade(pessoaIdade)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    public ResponseEntity<Pessoa> deletar(@PathVariable Long pessoaId) {
        if(!pessoaRepository.existsById(pessoaId)) {
            return ResponseEntity.notFound().build();
        }

        pessoaService.deletar(pessoaId);
        return ResponseEntity.noContent().build();
    }
}
