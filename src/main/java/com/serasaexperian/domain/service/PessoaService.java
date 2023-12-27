package com.serasaexperian.domain.service;

import com.serasaexperian.domain.exception.NegocioException;
import com.serasaexperian.domain.model.Endereco;
import com.serasaexperian.domain.model.Pessoa;
import com.serasaexperian.domain.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PessoaService {

    private final EnderecoService enderecoService;
    private final PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa cadastrar(Pessoa pessoa, String cep) throws Exception {
        Endereco endereco = enderecoService.buscarEnderecoPeloCep(cep);
        pessoa.setEndereco(endereco);

        Optional<Pessoa> pessoaJaCadastrada = pessoaRepository.findByNome(pessoa.getNome());

        if (pessoaJaCadastrada.isPresent()) {
            throw new NegocioException("Já existe uma pessoa cadastrado com este nome");
        }

        return pessoaRepository.save(pessoa);
    }

    public Page<Pessoa> listaPessoasPaginadas(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    @Transactional
    public void deletar(Long pessoaId) {
        pessoaRepository.deleteById(pessoaId);
    }
}
