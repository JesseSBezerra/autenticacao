package br.com.jdsb.autenticator.repositories;

import br.com.jdsb.autenticator.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByLogin(String login);

}
