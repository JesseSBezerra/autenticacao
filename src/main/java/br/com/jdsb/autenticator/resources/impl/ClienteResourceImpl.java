package br.com.jdsb.autenticator.resources.impl;

import br.com.jdsb.autenticator.resources.ClienteResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteResourceImpl implements ClienteResource {
    @Override
    public ResponseEntity<String> getCliente() {
        return ResponseEntity.ok().body("Funcionando");
    }
}
