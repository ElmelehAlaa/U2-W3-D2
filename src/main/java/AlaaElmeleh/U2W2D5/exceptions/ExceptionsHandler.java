package AlaaElmeleh.U2W2D5.exceptions;

import AlaaElmeleh.U2W2D5.payload.entity.ErrorsResponseDTO;
import AlaaElmeleh.U2W2D5.payload.entity.ErrorsResponseWithListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsResponseWithListDTO handleBadRequest(BadRequestException e){
        if(e.getErrorsList() != null) {
            List<String> errorsList = e.getErrorsList().stream().map(objectError -> objectError.getDefaultMessage()).toList();
            return new ErrorsResponseWithListDTO(e.getMessage(), new Date(), errorsList);
        } else {
            return new ErrorsResponseWithListDTO(e.getMessage(), new Date(), new ArrayList<>());
        }

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    public ErrorsResponseDTO handleNotFound(NotFoundException e){
        return new ErrorsResponseDTO(e.getMessage(), new Date());
    }
    @ExceptionHandler(DispositivoGiaAssegnatoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    public ErrorsResponseDTO handleDispositivoGiàAssegnato(DispositivoGiaAssegnatoException e) {
        return new ErrorsResponseDTO(e.getMessage(), new Date());
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    public ErrorsResponseDTO handleGeneric(Exception e){
        e.printStackTrace();
        return new ErrorsResponseDTO("Problema lato server", new Date());
    }
}
