package AlaaElmeleh.U2W2D5.services;

import AlaaElmeleh.U2W2D5.Enum.Role;
import AlaaElmeleh.U2W2D5.entities.Utente;
import AlaaElmeleh.U2W2D5.exceptions.BadRequestException;
import AlaaElmeleh.U2W2D5.exceptions.UnauthorizedException;
import AlaaElmeleh.U2W2D5.payload.entity.NewUtenteDTO;
import AlaaElmeleh.U2W2D5.payload.entity.UtenteLoginDTO;
import AlaaElmeleh.U2W2D5.repositories.UtentiRepository;
import AlaaElmeleh.U2W2D5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private PasswordEncoder bcrypt;


    public String authenticateUser(UtenteLoginDTO body){
        Utente utente = utenteService.findByEmail(body.email());
        // se lo trovo verifico la pass
       if(bcrypt.matches(body.password(),utente.getPassword())){
            return jwtTools.createToken(utente);
        }else {
            throw new UnauthorizedException("credenziali non valide!");
        }
    }
    public Utente save(NewUtenteDTO body) throws IOException {
        utentiRepository.findByEmail(body.email()).ifPresent( utente -> {
            throw new BadRequestException("L'email "+ utente.getEmail() + " è già utilizzata!");
        });
        Utente newUtente = new Utente();
        newUtente.setNome(body.nome());
        newUtente.setEmail(body.email());
        newUtente.setCognome(body.cognome());
        newUtente.setUsername(body.username());
        newUtente.setRole(Role.USER);
        newUtente.setPassword(bcrypt.encode(body.password()));
        return utentiRepository.save(newUtente);

    }
}
