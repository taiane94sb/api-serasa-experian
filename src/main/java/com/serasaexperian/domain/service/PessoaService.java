package com.serasaexperian.domain.service;

import com.serasaexperian.domain.model.Endereco;
import com.serasaexperian.domain.model.Pessoa;
import com.serasaexperian.domain.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PessoaService {

    private final EnderecoService enderecoService;
    private final PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa cadastrar(Pessoa pessoa, String cep) throws Exception {
        Endereco endereco = enderecoService.buscarEnderecoPeloCep(cep);
        pessoa.setEndereco(endereco);

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void deletar(Long pessoaId) {
        pessoaRepository.deleteById(pessoaId);
    }
}
