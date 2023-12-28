package com.serasaexperian.api.controller;

import com.serasaexperian.api.assembler.PessoaAssembler;
import com.serasaexperian.api.model.input.PessoaInput;
import com.serasaexperian.api.model.output.PessoaOutput;
import com.serasaexperian.domain.exception.NegocioException;
import com.serasaexperian.domain.model.Pessoa;
import com.serasaexperian.domain.repository.PessoaRepository;
import com.serasaexperian.domain.service.EnderecoService;
import com.serasaexperian.domain.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoa", description = "Pessoa")
public class PessoaController {

    private final PessoaService pessoaService;
    private final EnderecoService enderecoService;
    private final PessoaRepository pessoaRepository;
    private final ModelMapper modelMapper;
    private final PessoaAssembler pessoaAssembler;

    @GetMapping("listarPessoas")
    @Operation(summary = "Listagem de pessoas cadastradas", description = "Retorna a lista de pessoas cadastradas")
    public List<PessoaOutput> listar() {
        return pessoaAssembler.toCollectionModel(pessoaRepository.findAll());
    }

    @GetMapping("listarPessoasPaginadas")
    @Operation(summary = "Listagem páginada de pessoas cadastradas", description = "Retorna a lista páginada de pessoas cadastradas")
    public List<PessoaOutput> listarPessoasPaginadas(
            @PageableDefault(sort = "nome",
                    direction = Sort.Direction.ASC,
                    page = 0,
                    size = 2) Pageable page) {
        return pessoaAssembler.toCollectionModel(pessoaService.listaPessoasPaginadas(page).getContent());
    }

    @GetMapping("porId/{pessoaId}")
    @Operation(summary = "Pessoa cadastrada que possui o id fornecido", description = "Retorna a pessoa baseado no id fornecido")
    public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable Long pessoaId) {
        return pessoaRepository.findById(pessoaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("porNome/{pessoaNome}")
    @Operation(summary = "Pessoa cadastrada que possui o nome fornecido", description = "Retorna a pessoa baseado no nome fornecido")
    public ResponseEntity<Pessoa> buscarPessoaPorNome(@PathVariable String pessoaNome) {
        return pessoaRepository.findByNome(pessoaNome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("porIdade/{pessoaIdade}")
    @Operation(summary = "Pessoa cadastrada que possui a idade fornecida", description = "Retorna a pessoa baseado na idade fornecida")
    public ResponseEntity<List<Pessoa>> buscarPessoaPorIdade(@PathVariable Integer pessoaIdade) {
        return pessoaRepository.findByIdade(pessoaIdade)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("porCep/{pessoaCep}")
    @Operation(summary = "Pessoa cadastrada que possui o cep fornecida", description = "Retorna a pessoa baseado no cep fornecida")
    public ResponseEntity<List<Pessoa>> buscarPessoaPorCep(@PathVariable String pessoaCep) {
        return pessoaRepository.findByEndereco_Cep(pessoaCep)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma pessoa com as informações fornecidas", description = "Cadastra uma pessoa baseadas nas informações fornecidas")
    @SecurityRequirement(name = "jwt_auth")
    public PessoaOutput cadastrar(@Valid @RequestBody PessoaInput pessoaInput) {
        Pessoa novaPessoa = pessoaAssembler.toEntity(pessoaInput);
        Pessoa pessoaCadastrada = null;

        try {
            pessoaCadastrada = pessoaService.cadastrar(novaPessoa, pessoaInput.getCep());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        PessoaOutput pessoaOutput = pessoaAssembler.toModel(pessoaCadastrada);
        String pessoaScore = pessoaService.descricaoScore(pessoaOutput.getScore());
        pessoaOutput.setScoreDescription(pessoaScore);
        return pessoaOutput;
    }

    @DeleteMapping("/{pessoaId}")
    @Operation(summary = "Remove uma pessoa que possui o id fornecido", description = "Remove uma pessoa baseado no id fornecido")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Pessoa> deletar(@PathVariable Long pessoaId) {
        if(!pessoaRepository.existsById(pessoaId)) {
            return ResponseEntity.notFound().build();
        }

        pessoaService.deletar(pessoaId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{pessoaId}")
    @Operation(summary = "Atualiza uma pessoa que possui o id fornecido", description = "Atualiza uma pessoa baseado no id fornecido")
    @SecurityRequirement(name = "jwt_auth")
    public PessoaOutput atualizar(@PathVariable Long pessoaId,
                                                  @Valid @RequestBody PessoaInput pessoaInput) {
        Pessoa pessoaAtualizada = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new NegocioException("Pessoa não encontrada"));

        Pessoa novaPessoa = pessoaAssembler.toEntity(pessoaInput);

        try {
            enderecoService.deletar(pessoaAtualizada.getEndereco().getId());

            novaPessoa.setId(pessoaId);
            pessoaService.cadastrar(novaPessoa, pessoaInput.getCep());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        PessoaOutput pessoaOutput = pessoaAssembler.toModel(novaPessoa);
        String pessoaScore = pessoaService.descricaoScore(pessoaOutput.getScore());
        pessoaOutput.setScoreDescription(pessoaScore);

        return pessoaOutput;
    }
}
