package br.com.bootwithkafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicsConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /**
     *
     *cria e configura um bean do tipo KafkaAdmin que será gerenciado pelo Spring Framework.
     *Ele utiliza um mapa de configurações para definir os servidores Kafka a serem utilizados pelo
     * KafkaAdmin.
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    /**
     *
     * A instancia new NewTopic(1,2,3) é inicializada com 3 parametros
     * 1 - nome do tópico no Kafka.
     * 2 - Este é o número de partições do tópico.
     * 3 - Este é o fator de replicação do tópico.
     *      Ele define quantas réplicas do tópico serão mantidas no cluster Kafka
     */
    @Bean
    public NewTopic topic1() {
        return new NewTopic("teste-ricardo", 1, (short) 1);
    }

}
