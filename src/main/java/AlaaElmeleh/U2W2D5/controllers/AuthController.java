package AlaaElmeleh.U2W2D5.controllers;

import AlaaElmeleh.U2W2D5.entities.Utente;
import AlaaElmeleh.U2W2D5.exceptions.BadRequestException;
import AlaaElmeleh.U2W2D5.payload.entity.NewUtenteDTO;
import AlaaElmeleh.U2W2D5.payload.entity.UserLoginSuccessDTO;
import AlaaElmeleh.U2W2D5.payload.entity.UtenteLoginDTO;
import AlaaElmeleh.U2W2D5.services.AuthService;
import AlaaElmeleh.U2W2D5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UtenteService utenteService;






    @PostMapping("login")
    public UserLoginSuccessDTO login (@RequestBody UtenteLoginDTO body){
        return new UserLoginSuccessDTO(authService.authenticateUser(body));
    }







    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente saveUser(@RequestBody @Validated NewUtenteDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            try{
                return utenteService.save(body);
            }catch (IOException e ){
                throw  new RuntimeException(e);
            }
        }
    }
}
