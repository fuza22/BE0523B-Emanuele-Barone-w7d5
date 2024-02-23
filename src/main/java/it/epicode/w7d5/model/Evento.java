package it.epicode.w7d5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.w7d5.exception.OutOfAvailabilityException;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nomeEvento;
    private String descrizione;
    private LocalDate data;
    private String location;
    private int maxUtenti;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "evento_utente",
    joinColumns = @JoinColumn(name = "evento_fk"),
    inverseJoinColumns = @JoinColumn(name = "utente_fk"))
    private List<Utente> utenti;

    public void setUtente(Utente utente) throws OutOfAvailabilityException{

        utenti.add(utente);

    }

}
