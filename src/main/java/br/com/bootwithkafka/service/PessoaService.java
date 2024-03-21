package br.com.bootwithkafka.service;

import br.com.bootwithkafka.dto.PessoaRequestDTO;
import br.com.bootwithkafka.entity.Pessoa;
import br.com.bootwithkafka.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ModelMapper modelMapper;


    public void enviarParaAFila(PessoaRequestDTO pessoa){
        String pessoaJson = "";

        try{
           pessoaJson = objectMapper.writeValueAsString(pessoa);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        kafkaTemplate.send("topic-teste",pessoaJson);
    }

    public List<PessoaRequestDTO> retornaPessoas(){
        return this.pessoaRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, PessoaRequestDTO.class)).toList();
    }

    @KafkaListener(topicPartitions
            = @TopicPartition(topic = "topic-teste", partitions = { "0", "1" }))
    public void listenToPartition(@Payload String message) {
        System.out.println("Executando o consumer");
        System.out.println("payload: "+message);
        Pessoa pessoa = null;
        try{
            pessoa = objectMapper
                    .reader()
                    .forType(Pessoa.class)
                    .readValue(message);
        }catch(Exception e){
            System.out.println("Erro ao salvar pessoa: "+e.getMessage());
        }

        if(pessoa != null)
        this.pessoaRepository.save(pessoa);
    }


}
