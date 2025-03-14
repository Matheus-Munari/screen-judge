package com.filmes.avaliador.config;

import com.filmes.avaliador.dto.response.email.ComentarioAvaliacaoEmailDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaComentarioAvaliacaoEmailConsumerConfiguration {

    @Bean(name = "consumerFactoryComentarioAvaliacao")
    public ConsumerFactory<String, ComentarioAvaliacaoEmailDTO> comentarioEmailConsumerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "comentario_avaliacao_email");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // Define a classe do objeto esperado
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.filmes.avaliador.dto.response.email.ComentarioAvaliacaoEmailDTO");

        // Garante que o Consumer confia no pacote onde a classe está localizada
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        // Configura o deserializador para lidar com erros
        configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ComentarioAvaliacaoEmailDTO> kafkaComentarioEmailListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, ComentarioAvaliacaoEmailDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(comentarioEmailConsumerFactory());
        return factory;
    }


}
