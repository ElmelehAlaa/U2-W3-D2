package AlaaElmeleh.U2W2D5.services;

import AlaaElmeleh.U2W2D5.Enum.Role;
import AlaaElmeleh.U2W2D5.entities.Utente;
import AlaaElmeleh.U2W2D5.exceptions.BadRequestException;
import AlaaElmeleh.U2W2D5.exceptions.NotFoundException;
import AlaaElmeleh.U2W2D5.payload.entity.NewUtenteDTO;
import AlaaElmeleh.U2W2D5.repositories.UtentiRepository;
import com.cloudinary.Cloudinary;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UtenteService {
    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinary;
    public Utente save(NewUtenteDTO body) throws IOException{
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
    public Page<Utente> getUtenti (int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return utentiRepository.findAll(pageable);
    }

    public Utente findById(int id) throws NotFoundException{
        return utentiRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public void findByIdAndDelete(int id) throws NotFoundException{
        Utente found = this.findById(id);
        utentiRepository.delete(found);
    }

    public Utente findByIdAndUpdate(int id, Utente body)throws NotFoundException{
        Utente found = this.findById(id);
        found.setCognome(body.getCognome());
        found.setNome(body.getNome());
        found.setEmail(body.getEmail());
        found.setUsername(body.getUsername());
        return utentiRepository.save(found);
    }
    public Utente uploadPicture(MultipartFile file, @PathVariable int id)throws IOException{
        Utente foundUtente= this.findById(id);
        String cloudinaryURL = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        foundUtente.setAvatarUrl(cloudinaryURL);
        return utentiRepository.save(foundUtente);
    }

    public Utente findByEmail(String email){
        return utentiRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("utente con email" + email + "non trovato!"));
    }

}
