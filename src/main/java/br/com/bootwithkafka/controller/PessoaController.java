package br.com.bootwithkafka.controller;

import br.com.bootwithkafka.dto.PessoaRequestDTO;
import br.com.bootwithkafka.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pessoa")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping()
    public ResponseEntity<Void> criarPessoa(@RequestBody PessoaRequestDTO pessoa){
        this.pessoaService.enviarParaAFila(pessoa);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PessoaRequestDTO>> retornarPessoas(){
        return ResponseEntity.ok().body(this.pessoaService.retornaPessoas());
    }
}
