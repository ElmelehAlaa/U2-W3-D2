package AlaaElmeleh.U2W2D5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String avatarUrl;
    @OneToMany(mappedBy = "utenteAssegnato",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dispositivo> dispositivo;
    private String password;
    @CreationTimestamp
    private Date createdAt;
}
