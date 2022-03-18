package com.will.bank.resource;

import com.will.bank.service.TransacoesContingenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("faz-tudo")
public class FazTudoResource {

    private final TransacoesContingenciaService service;

    @Autowired
    public FazTudoResource(TransacoesContingenciaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> ajustar() {
        service.reenviarPorEmail("sheridancrist@lemke.info");
        return new ResponseEntity<>("Transação do cliente reenviada.", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> ajustar(@RequestBody String email) {
        service.reenviarPorEmail(email);
        return new ResponseEntity<>("Transação do cliente reenviada.", HttpStatus.OK);
    }
}
