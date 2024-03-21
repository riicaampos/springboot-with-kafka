package br.com.bootwithkafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /**
     * ConsumerFactory<String, String> este método irá criar e retornar
     * uma fábrica de consumidores capaz de consumir mensagens com chaves e valores do
     * tipo String.
     *
     * Este método cria e configura uma fábrica de consumidores capaz
     * de criar consumidores para o Apache Kafka. As configurações incluem os endereços dos
     * servidores Kafka, o ID do grupo do consumidor, além dos deserializadores para as
     * chaves e valores das mensagens. Este bean pode ser injetado em outros componentes
     * da aplicação que precisam interagir com o Kafka como consumidores.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {

        Map<String, Object> props = new HashMap<>();
        //Endereço do servidor kafka
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);

        //O ID do grupo é usado para identificar um grupo de consumidores que compartilham
        // a carga de trabalho de ler mensagens de um ou mais tópicos Kafka
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "foo");

        //define o deserializador de chaves para o consumidor. No caso, está sendo utilizado
        // StringDeserializer, que é uma classe fornecida pelo próprio
        // Kafka para desserializar chaves do tipo String.
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        //define o deserializador de valores para o consumidor. Assim como para as chaves,
        // está sendo utilizado StringDeserializer para desserializar os valores do tipo String.
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     *
     * ConcurrentKafkaListenerContainerFactory<String, String> Cria e retornar
     * uma fábrica de contêineres de ouvintes do Kafka capaz de lidar com mensagens
     * com chaves e valores do tipo String.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory() {

        //instancia responsável por criar os contêineres de ouvintes do Kafka
        // que irão escutar as mensagens nos tópicos Kafka.
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        //configura a fábrica de consumidores que será utilizada pelos contêineres
        // de ouvintes. O método consumerFactory() retorna a fábrica de consumidores
        // configurada anteriormente, que define como o consumidor será criado
        // e configurado para consumir mensagens do Kafka.
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
