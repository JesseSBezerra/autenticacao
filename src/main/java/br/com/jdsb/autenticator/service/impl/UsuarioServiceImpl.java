package br.com.jdsb.autenticator.service.impl;

import br.com.jdsb.autenticator.entity.Usuario;
import br.com.jdsb.autenticator.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class
UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario create(Usuario usuario){
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Usuario usuario = usuarioRepository.findByLogin(username).
               orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado na base de dados"));
       String[] roles = usuario.isAdmin() ? new String[]{"ADMIN","USER"} : new String[]{"USER"};
       return User
               .builder()
               .username(usuario.getLogin())
               .password(usuario.getSenha())
               .roles(roles)
               .build();
    }
}
