package AlaaElmeleh.U2W2D5.controllers;
import AlaaElmeleh.U2W2D5.entities.Dispositivo;
import AlaaElmeleh.U2W2D5.exceptions.BadRequestException;
import AlaaElmeleh.U2W2D5.exceptions.DispositivoGiaAssegnatoException;
import AlaaElmeleh.U2W2D5.exceptions.NotFoundException;
import AlaaElmeleh.U2W2D5.payload.entity.NewDispositivoDTO;
import AlaaElmeleh.U2W2D5.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/devices")
public class DispositivoController {
    @Autowired
    private DispositivoService dispositiviService;

    @GetMapping("")
    public Page<Dispositivo> getDispositivi(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10")int size,
                                            @RequestParam(defaultValue = "id")String orderBy){
        return  dispositiviService.getDispositivi(page,size,orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Dispositivo saveDispositivo(@RequestBody @Validated NewDispositivoDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return dispositiviService.save(body);
        }
    }

    @GetMapping(value = "/{id}")
    public Dispositivo findById(@PathVariable int id){return dispositiviService.findById(id);}

    @PutMapping("/{id}")
    public Dispositivo findByIdAndUpdate(@PathVariable int id , @RequestBody @Validated NewDispositivoDTO body,BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return dispositiviService.findByIdAndUpdate(id, body);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int id){
        dispositiviService.findByIdAndDelete(id);
    }

    @PatchMapping("/assegna/{utenteId}/{dispositivoId}")
    public Dispositivo assegnaDispositivoAdUnUtente(@PathVariable int utenteId,
                                               @PathVariable int dispositivoId){
        try {
            return dispositiviService.assegnaDispositivoAdUtente(utenteId, dispositivoId);
        } catch (DispositivoGiaAssegnatoException e) {
            throw e;
        } catch (NotFoundException e) {
            throw new NotFoundException(e);
        }
    }
}
