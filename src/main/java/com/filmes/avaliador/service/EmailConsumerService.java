package com.filmes.avaliador.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmes.avaliador.dto.response.filme.FilmeResponseDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumerService {

    @KafkaListener(topics = "teste-topico", groupId = "meu-grupo")
    public void consumirMensagem(String mensagem) {

        System.out.println("Filme recebido: " + mensagem);
    }


}
