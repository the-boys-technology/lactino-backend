package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.service.EnderecoService;
import br.com.tbt.lactino.service.dto.ViaCepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final WebClient webClient = WebClient.create("https://viacep.com.br/ws");

    @Override
    public ViaCepResponse buscarEnderecorPorCep(String cep) {
        try {
            return webClient
                    .get()
                    .uri("/{cep}/json/", cep)
                    .retrieve()
                    .bodyToMono(ViaCepResponse.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("CEP não encontrado.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar o CEP: " + e.getMessage());
        }
    }
}
