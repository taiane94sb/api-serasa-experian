package com.serasaexperian.domain.repository;


import com.serasaexperian.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>  {

    Optional<Pessoa> findByNome(String nome);
    Optional<List<Pessoa>> findByIdade(Integer idade);

    Optional<List<Pessoa>> findByEndereco_Cep(String cep);
}
