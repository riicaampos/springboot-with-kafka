package br.com.bootwithkafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /**
     * este método irá criar e retornar uma fábrica de produtores capaz de
     * produzir mensagens com chaves e valores do tipo String.
     *
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {

        Map<String, Object> configProps = new HashMap<>();

        //endereços dos servidores Kafka a serem usados pelo produtor
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);

        //define o serializador de chaves para o produtor. No caso, está sendo
        // utilizado StringSerializer, que é uma classe fornecida pelo próprio Kafka
        // para serializar chaves do tipo String.
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        //define o serializador de valores para o produtor. Assim como para as chaves,
        // está sendo utilizado StringSerializer para serializar os valores do tipo String.
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        //implementação da interface ProducerFactory. Esta instância é inicializada
        // com as configurações contidas no mapa configProps. A fábrica de produtores
        // configurada desta forma será capaz de criar produtores capazes de produzir
        // mensagens do Kafka de acordo com as configurações especificadas.
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     *cria e configura um bean do tipo KafkaTemplate, que é usado para enviar mensagens
     * para um tópico no Apache Kafka
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
