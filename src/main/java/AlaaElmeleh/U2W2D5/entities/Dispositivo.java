package AlaaElmeleh.U2W2D5.entities;

import AlaaElmeleh.U2W2D5.Enum.StatoDispositivo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descrizione;

    @Enumerated(EnumType.STRING)
    private StatoDispositivo stato;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utenteAssegnato;
}
