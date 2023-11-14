package AlaaElmeleh.U2W2D5.repositories;

import AlaaElmeleh.U2W2D5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtentiRepository extends JpaRepository<Utente,Integer> {

    Optional<Utente> findByEmail(String email);
}
