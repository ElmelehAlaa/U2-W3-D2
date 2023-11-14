package AlaaElmeleh.U2W2D5.security;

import AlaaElmeleh.U2W2D5.entities.Utente;
import AlaaElmeleh.U2W2D5.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${JWT_SECRET}")
    private String secret;

    public String createToken(Utente utente){
        return Jwts.builder().setSubject(String.valueOf(utente.getId())) // SOGGETTO
                .setIssuedAt(new Date(System.currentTimeMillis()))// EMISSIONE
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24 * 7) ) //SCADENZA
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }//LOGIN
    public void verifyToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        }catch (Exception ex){
            throw new UnauthorizedException("Il token non è valido! rifai il login");
        }
    } // Lo utilizzo in tutte le request autenticate

    public String extractIdFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject();
        // Nel payload del token ci sono il subject( che è l'ID dell'utente), la data di emissione, e la data di scadenza.
        // A noi interessa l'id dell'utente
    }
}
