package br.com.jdsb.autenticator.resources.impl;

import br.com.jdsb.autenticator.entity.Usuario;
import br.com.jdsb.autenticator.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuario/")
public class UsuarioResourceImpl {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario usuario){
        return ResponseEntity.ok().body(usuarioService.create(usuario));
    }
}
