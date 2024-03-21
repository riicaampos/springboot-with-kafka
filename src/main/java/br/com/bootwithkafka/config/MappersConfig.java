package br.com.bootwithkafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MappersConfig {

    /**
     * para converter objeto em outro objeto, por exemplo quando se tem um DTO e
     * precisa converter esse DTO para uma entidade a ser persistidada no banco
     */
    @Bean
    public ModelMapper modelMapper() {
       return new ModelMapper();
    }

    /**
     * para converter objeto em String no formato Json para ser enviado a fila
     * quando for consumir, precisa converter o Json em objeto novamente.
     */
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
