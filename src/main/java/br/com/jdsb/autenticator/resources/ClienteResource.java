package br.com.jdsb.autenticator.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/cliente")
public interface ClienteResource {

    @GetMapping
    public ResponseEntity<String> getCliente();
}
