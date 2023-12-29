package com.serasaexperian.domain.service;


import com.serasaexperian.domain.exception.NegocioException;
import com.serasaexperian.domain.model.Endereco;
import com.serasaexperian.domain.model.Pessoa;
import com.serasaexperian.domain.repository.PessoaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private PessoaRepository pessoaRepository;


    @Test
    @DisplayName("Should return Insuficiente as score description")
    public void should_return_insuficiente_as_score_description() {
        String scoreDescription1 = pessoaService.descricaoScore(0);
        String scoreDescription2 = pessoaService.descricaoScore(200);

        Assertions.assertThat(scoreDescription1).isEqualTo("Insuficiente");
        Assertions.assertThat(scoreDescription2).isEqualTo("Insuficiente");
    }

    @Test
    @DisplayName("Should return Inaceitável as score description")
    public void should_return_inaceitavel_as_score_description() {
        String scoreDescription1 = pessoaService.descricaoScore(201);
        String scoreDescription2 = pessoaService.descricaoScore(500);

        Assertions.assertThat(scoreDescription1).isEqualTo("Inaceitável");
        Assertions.assertThat(scoreDescription2).isEqualTo("Inaceitável");
    }

    @Test
    @DisplayName("Should return Aceitável as score description")
    public void should_return_aceitavel_as_score_description() {
        String scoreDescription1 = pessoaService.descricaoScore(501);
        String scoreDescription2 = pessoaService.descricaoScore(700);

        Assertions.assertThat(scoreDescription1).isEqualTo("Aceitável");
        Assertions.assertThat(scoreDescription2).isEqualTo("Aceitável");
    }

    @Test
    @DisplayName("Should return Recomendável as score description")
    public void should_return_recomendavel_as_score_description() {
        String scoreDescription1 = pessoaService.descricaoScore(701);
        String scoreDescription2 = pessoaService.descricaoScore(1000);

        Assertions.assertThat(scoreDescription1).isEqualTo("Recomendável");
        Assertions.assertThat(scoreDescription2).isEqualTo("Recomendável");
    }

    @Test
    @DisplayName("Should register a new Pessoa")
    public void should_register_a_new_pessoa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Taiane");
        pessoa.setIdade(29);
        pessoa.setTelefone("911111111");
        pessoa.setScore(1000);

        when(enderecoService.buscarEnderecoPeloCep(any())).thenReturn(new Endereco());
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa createdPessoa = pessoaService.cadastrar(pessoa, "41111111");

        Assertions.assertThat(createdPessoa).isNotNull();
    }

    @Test
    @DisplayName("Should throw a NegocioException when try to register a Pessoa that already exists")
    public void should_throw_a_negocio_exception_when_try_to_register_a_pessoa_that_already_exists() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Taiane");
        pessoa.setIdade(29);
        pessoa.setTelefone("911111111");
        pessoa.setScore(1000);

        when(enderecoService.buscarEnderecoPeloCep(any())).thenReturn(new Endereco());
        when(pessoaRepository.findByNome(any())).thenReturn(Optional.of(pessoa));


        try {
            pessoaService.cadastrar(pessoa, "41111111");
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(NegocioException.class);
        }
    }

    @Test
    @DisplayName("Should list pagination")
    public void should_list_pagination() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Taiane");
        pessoa.setIdade(29);
        pessoa.setTelefone("988888888");
        pessoa.setScore(1000);

        Page<Pessoa> pessoas = Page.empty();

        when(pessoaService.listaPessoasPaginadas(any())).thenReturn(pessoas);
        Page<Pessoa> pessoaPage = pessoaService.listaPessoasPaginadas(any(Pageable.class));

        Assertions.assertThat(pessoaPage).isEmpty();
    }

    @Test
    @DisplayName("Should remove a Pessoa")
    public void should_remove_a_pessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Taiane");
        pessoa.setIdade(29);
        pessoa.setTelefone("911111111");
        pessoa.setScore(1000);

        doNothing().when(pessoaRepository).deleteById(pessoa.getId());

        pessoaService.deletar(pessoa.getId());

        verify(pessoaRepository, times(1)).deleteById(anyLong());
    }
}
