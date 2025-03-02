package com.filmes.avaliador.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmes.avaliador.dto.response.email.EmailMessageDTO;
import com.filmes.avaliador.dto.response.filme.FilmeResponseDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumerService {

    @KafkaListener(topics = "teste-topico", groupId = "meu-grupo")
    public void consumirMensagem(EmailMessageDTO mensagem) {
        
        System.out.println("Filme recebido: " + mensagem);
    }

    @KafkaListener(topics = "email-sender", groupId = "meu-grupo")
    public void enviarEmail(EmailMessageDTO mensagem) {

        System.out.println("Destinatário: " + mensagem.getDestinatario());
        System.out.println("Corpo: " + mensagem.getCorpo());
    }


}
