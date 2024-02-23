package it.epicode.w7d5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.w7d5.exception.UnAuthorizedException;
import it.epicode.w7d5.model.Utente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource("application.properties")
public class JwtTools {

    @Value("${spring.jwt.secret}")
    private String secret;
    @Value("${spring.jwt.expirationMs}")
    private String expirationMs;


    public String createToken(Utente utente){
        return Jwts.builder().subject(utente.getEmail()).issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis()+Long.parseLong(expirationMs))).
                signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

    }

    public void validateToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        }
        catch (Exception e){
            throw new UnAuthorizedException(e.getMessage());
        }
    }

    public String extractEmailFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).
                getPayload().getSubject();
    }

}
