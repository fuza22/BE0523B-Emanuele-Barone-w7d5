package it.epicode.w7d5.repository;

import it.epicode.w7d5.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer>, PagingAndSortingRepository<Utente, Integer> {

    public Optional<Utente> findByEmail(String email);
    public Optional<Utente> deleteByEmail(String email);

}
