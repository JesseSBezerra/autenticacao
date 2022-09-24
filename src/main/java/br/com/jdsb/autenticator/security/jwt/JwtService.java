package br.com.jdsb.autenticator.security.jwt;

import br.com.jdsb.autenticator.AutenticatorApplication;
import br.com.jdsb.autenticator.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expircao;

    @Value("${security.jwt.chavate-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario){
        long expString = Long.valueOf(expircao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512,chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException{
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token){
      try {
          Claims claims = obterClaims(token);
          Date dataExpiration = claims.getExpiration();
          LocalDateTime data = dataExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
          return !LocalDateTime.now().isAfter(data);
      }catch (Exception e){
          return false;
      }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context =  SpringApplication.run(AutenticatorApplication.class);
        JwtService service = context.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("jesse").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);
        boolean isTolekValido = service.tokenValido(token);
        System.out.println("O token esta valido "+isTolekValido);

        String login = service.obterLoginUsuario(token);
        System.out.println("O usuario é "+login);
    }


}
