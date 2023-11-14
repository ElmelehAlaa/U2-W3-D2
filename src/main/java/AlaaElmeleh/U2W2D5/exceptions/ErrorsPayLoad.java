package AlaaElmeleh.U2W2D5.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class ErrorsPayLoad {
    private String message;
    private Date timestamp;

}