package com.filmes.avaliador.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmes.avaliador.dto.response.email.ComentarioAvaliacaoEmailDTO;
import com.filmes.avaliador.dto.response.email.EmailMessageDTO;
import com.filmes.avaliador.dto.response.filme.FilmeResponseDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class EmailConsumerService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @KafkaListener(topics = "teste-topico", groupId = "meu-grupo")
    public void consumirMensagem(EmailMessageDTO mensagem) {
        
        System.out.println("Filme recebido: " + mensagem);
    }

    @KafkaListener(topics = "email-sender", groupId = "meu-grupo", containerFactory = "kafkaListenerContainerFactory")
    public void enviarEmail(EmailMessageDTO mensagem) throws IOException, MessagingException {

        String templateHtml = lerArquivo("email-template.html");
        String conteudoHtml = templateHtml.replace("${USER}", mensagem.getNome());
        conteudoHtml = conteudoHtml.replace("${CODE}", mensagem.getCodigo());

        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress(remetente));
        message.setRecipients(MimeMessage.RecipientType.TO, mensagem.getDestinatario());
        message.setContent(conteudoHtml, "text/html; charset=utf-8");
        message.setSubject(mensagem.getAssunto());

        javaMailSender.send(message);
    }

    @KafkaListener(topics = "comentario_avaliacao_email", groupId = "comentario_avaliacao_consumer",  containerFactory = "kafkaComentarioEmailListenerContainerFactory")
    public void enviarEmailComentarioAvaliacao(ComentarioAvaliacaoEmailDTO comentario) throws IOException, MessagingException {
        String templateHtml = lerArquivo("email_comentario_avaliacao.html");

        String conteudoHtml = templateHtml.replace("${nome_usuario}", comentario.avaliacao().usuario().nome());
        conteudoHtml = conteudoHtml.replace("${nome_comentador}", comentario.comentario().usuario().nome());
        conteudoHtml = conteudoHtml.replace("${nome_filme}", comentario.avaliacao().filme().titulo());
        conteudoHtml = conteudoHtml.replace("${comentario}", comentario.comentario().comentario());

        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress(remetente));
        message.setRecipients(MimeMessage.RecipientType.TO, comentario.avaliacao().usuario().email());
        message.setContent(conteudoHtml, "text/html; charset=utf-8");
        message.setSubject("Novo comentário em sua avaliação");

        javaMailSender.send(message);
    }

    public String lerArquivo(String nomeArquivo) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/" + nomeArquivo);
        return new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);

    }


}
