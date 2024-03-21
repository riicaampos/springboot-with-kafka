package br.com.bootwithkafka.dto;

import lombok.Data;

@Data
public class PessoaRequestDTO {
    private String nome;

    private String email;

    PessoaRequestDTO(){
        super();
    }

    PessoaRequestDTO(String nome, String email){
        this.nome = nome;
        this.email = email;
    }
}
