package AlaaElmeleh.U2W2D5.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(int id){
        super("Elemento con id " + id + " non trovato!");
    }
    public NotFoundException(NotFoundException e){super(e.getMessage());}

    public NotFoundException(String email){
        super("Elemento con email " + email + " non trovato!");
    }
}
