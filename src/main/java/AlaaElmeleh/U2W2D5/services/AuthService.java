package AlaaElmeleh.U2W2D5.services;

import AlaaElmeleh.U2W2D5.entities.Utente;
import AlaaElmeleh.U2W2D5.exceptions.UnauthorizedException;
import AlaaElmeleh.U2W2D5.payload.entity.UtenteLoginDTO;
import AlaaElmeleh.U2W2D5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTools jwtTools;


    public String authenticateUser(UtenteLoginDTO body){
        Utente utente = utenteService.findByEmail(body.email());
        // se lo trovo verifico la pass
        if(body.password().equals(utente.getPassword())){
            return jwtTools.createToken(utente);
        }else {
            throw new UnauthorizedException("credenziali non valide!");
        }
    }
}
