package AlaaElmeleh.U2W2D5.payload.entity;

import AlaaElmeleh.U2W2D5.Enum.StatoDispositivo;
import AlaaElmeleh.U2W2D5.entities.Utente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewDispositivoDTO(@NotEmpty(message = "la descrizione é obbligatoria!")
                                String descrizione,
                                @NotNull(message = "lo stato è obbligatorio!")
                                StatoDispositivo stato,
                                Utente utenteAssegnato
                                ) {
}
