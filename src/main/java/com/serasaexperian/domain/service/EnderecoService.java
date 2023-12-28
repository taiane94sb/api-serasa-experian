package com.serasaexperian.domain.service;

import com.google.gson.Gson;
import com.serasaexperian.domain.model.Endereco;
import com.serasaexperian.domain.repository.EnderecoRepository;
import com.serasaexperian.util.Util;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@AllArgsConstructor
@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public Endereco buscarEnderecoPeloCep (String cep) throws Exception {
        String enderecoURL = "https://viacep.com.br/ws/" + cep + "/json/";
        URL url = new URL(enderecoURL);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        conexao.setDoInput(true);
        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader((conexao.getInputStream()), "utf-8"));

            String convertJsonString = Util.converterJsonEmString(buff);
            Gson gson = new Gson();
            return gson.fromJson(convertJsonString, Endereco.class);

        } catch (Exception msgErro) {
            throw  new Exception("Erro de conexão - status Code [" + conexao.getResponseCode() + "]. " + msgErro.toString());
        }
    }

    @Transactional
    public void deletar(Long enderecoId) {
        enderecoRepository.deleteById(enderecoId);
    }
}
