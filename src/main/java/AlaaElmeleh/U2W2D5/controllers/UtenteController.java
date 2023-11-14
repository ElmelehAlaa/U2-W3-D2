package AlaaElmeleh.U2W2D5.controllers;

import AlaaElmeleh.U2W2D5.entities.Utente;
import AlaaElmeleh.U2W2D5.exceptions.BadRequestException;
import AlaaElmeleh.U2W2D5.payload.entity.NewUtenteDTO;
import AlaaElmeleh.U2W2D5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UtenteController {
    @Autowired
    private UtenteService utentiService;

    @GetMapping("")
    public Page<Utente> getUtenti(@RequestParam (defaultValue = "0")int page,
                                  @RequestParam(defaultValue = "10")int size,
                                  @RequestParam(defaultValue = "id")String orderBy){
        return utentiService.getUtenti(page,size,orderBy);
    }

    @GetMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal UserDetails currentUser){
        return currentUser;
    };

    @PutMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal Utente currentUser, @RequestBody Utente body){
        return utentiService.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void getProfile(@AuthenticationPrincipal Utente currentUser){
        utentiService.findByIdAndDelete(currentUser.getId());
    };



    @GetMapping(value = "/{id}")
    public Utente findById(@PathVariable int id){return  utentiService.findById(id);}

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public  Utente findByIdAndUpdate(@PathVariable int id, @RequestBody @Validated Utente body,BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return utentiService.findByIdAndUpdate(id, body);
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int id){
        utentiService.findByIdAndDelete(id);
    }

    @PatchMapping("/{id}/avatar")
    public Utente uploadAvatar(@RequestParam("avatar")MultipartFile file,@PathVariable int id) throws IOException{
        return utentiService.uploadPicture(file,id);
    }
}

