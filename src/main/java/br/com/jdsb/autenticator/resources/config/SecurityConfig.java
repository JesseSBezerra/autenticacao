package br.com.jdsb.autenticator.resources.config;

import br.com.jdsb.autenticator.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

   @Autowired
   PasswordEncoder encoder;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /*
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
               .withUser("jesse")
               .password(passwordEncoder().encode("1234"))
               .roles("USER");
        */
        auth.userDetailsService(usuarioService)
                .passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/cliente/**")
                    .hasRole("USER")
                .antMatchers(HttpMethod.POST,"/api/usuario/**")
                    .permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}
