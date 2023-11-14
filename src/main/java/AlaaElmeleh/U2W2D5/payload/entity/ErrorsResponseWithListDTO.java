package AlaaElmeleh.U2W2D5.payload.entity;

import java.util.Date;
import java.util.List;

public record ErrorsResponseWithListDTO(String message,
                                        Date timestamp,
                                        List<String> errorsList)
{
}
