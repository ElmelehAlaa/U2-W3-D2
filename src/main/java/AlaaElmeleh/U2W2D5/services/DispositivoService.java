package AlaaElmeleh.U2W2D5.services;

import AlaaElmeleh.U2W2D5.entities.Dispositivo;
import AlaaElmeleh.U2W2D5.entities.Utente;
import AlaaElmeleh.U2W2D5.exceptions.DispositivoGiaAssegnatoException;
import AlaaElmeleh.U2W2D5.exceptions.NotFoundException;
import AlaaElmeleh.U2W2D5.payload.entity.NewDispositivoDTO;
import AlaaElmeleh.U2W2D5.repositories.DispositiviRepository;
import AlaaElmeleh.U2W2D5.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DispositivoService {
    @Autowired
    private DispositiviRepository dispositiviRepository;
    @Autowired
    private UtentiRepository utentiRepository;

    public Dispositivo save(NewDispositivoDTO body){
        Dispositivo newDispositivo = new Dispositivo();
        newDispositivo.setDescrizione(body.descrizione());
        newDispositivo.setStato(body.stato());
        newDispositivo.setUtenteAssegnato(body.utenteAssegnato());
        return dispositiviRepository.save(newDispositivo);
    }

    public Page<Dispositivo>getDispositivi(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return dispositiviRepository.findAll(pageable);
    }
    public Dispositivo findById(int id) throws NotFoundException{
        return dispositiviRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws NotFoundException{
        Dispositivo found = this.findById(id);
        dispositiviRepository.delete(found);
    }
    public Dispositivo findByIdAndUpdate (int id , NewDispositivoDTO body)throws  NotFoundException{
        Dispositivo found = this.findById(id);
        found.setStato(body.stato());
        found.setDescrizione(body.descrizione());
        return dispositiviRepository.save(found);
    }

    public Dispositivo assegnaDispositivoAdUtente(int utenteId, int dispositivoId){
        Utente utente = utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        Dispositivo dispositivo = dispositiviRepository.findById(dispositivoId).orElseThrow(() -> new NotFoundException(dispositivoId));
        if (dispositivo.getUtenteAssegnato() != null) {
            throw new DispositivoGiaAssegnatoException("Dispositivo gia' assegnato ad un utente!");
        }
        dispositivo.setUtenteAssegnato(utente);
return        dispositiviRepository.save(dispositivo);

    }

}
