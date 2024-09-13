package com.fatecrl.contacorrente.controller;

import java.net.URI;
import java.rmi.server.ServerCloneException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Servlet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
/*01 import org.springframework.web.bind.annotation.GetMapping;*/
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.contacorrente.model.Conta;
import com.fatecrl.contacorrente.service.ContaService;

@RestController
@RequestMapping("/conta-corrente")
public class ContaCorrenteController {

    @Autowired
    private ContaService contaService;

    /*@GetMapping
    public List<Conta> getAll(){
        return contaService.findAll();
    }
    */
    
//retorno da lista de conta
//http://localhost:8090/api/conta-corrente (Lista)

    @GetMapping
    public ResponseEntity<List<Conta>> getAll(){
        return ResponseEntity.ok(contaService.findAll());
    }

//http://localhost:8090/api/conta-corrente/1 (ok)
//http://localhost:8090/api/conta-corrente/2 (Not Found)

    @GetMapping("/{id}")
    public ResponseEntity<Conta> get(@PathVariable("id") Long id){
        Conta conta = contaService.find(id);

        if (conta != null){
            return ResponseEntity.ok(conta);
        }
        return ResponseEntity.notFound().build();
        //return ResponseEntity.ok(contaService.find(id));
    }

    @PostMapping
    public ResponseEntity<Conta> create(@RequestBody Conta conta){
        contaService.Create(conta);
        URI location  = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(conta.getId())
                            .toUri();
        return ResponseEntity.created(location).body(conta);

}

    @PutMapping
        public ResponseEntity<Conta> update(@RequestBody Conta conta){
             if(contaService.update(conta)){
                return ResponseEntity.ok(conta);
        }
         return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Conta> delete(@PathVariable Long id){
        if (contaService.delete(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

/*  
01-
    @GetMapping("/obter")
    public String get(){
    return "conteudo";

}
    ResponseEntity <Conta> podemos enviar "404", "200", xml, respostas, comunicação da api.
*/




