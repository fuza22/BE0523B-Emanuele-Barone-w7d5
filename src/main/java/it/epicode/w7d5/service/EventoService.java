package it.epicode.w7d5.service;

import it.epicode.w7d5.exception.AlreadyAssignedException;
import it.epicode.w7d5.exception.DifferentUserException;
import it.epicode.w7d5.exception.OutOfAvailabilityException;
import it.epicode.w7d5.exception.NotFoundException;
import it.epicode.w7d5.model.Evento;
import it.epicode.w7d5.model.Utente;
import it.epicode.w7d5.repository.EventoRepository;
import it.epicode.w7d5.request.EventoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    UtenteService utenteService;

    public Page<Evento> getAll(Pageable pageable){
        return eventoRepository.findAll(pageable);
    }

    public Evento getEventoById(int id) throws NotFoundException {
        return eventoRepository.findById(id).orElseThrow(()->new NotFoundException("Evento con id = " + id + " non trovato"));
    }

    public Evento saveEvento(EventoRequest eventoRequest){
        Evento e = new Evento();
        e.setNomeEvento(eventoRequest.getNomeEvento());
        e.setDescrizione(eventoRequest.getDescrizione());
        e.setData(eventoRequest.getData());
        e.setLocation(eventoRequest.getLocation());
        e.setMaxUtenti(eventoRequest.getMaxUtenti());

        return eventoRepository.save(e);
    }

    public Evento updateEvento(int id, EventoRequest eventoRequest) throws NotFoundException {
        Evento e = getEventoById(id);
        e.setNomeEvento(eventoRequest.getNomeEvento());
        e.setDescrizione(eventoRequest.getDescrizione());
        e.setData(eventoRequest.getData());
        e.setLocation(eventoRequest.getLocation());
        e.setMaxUtenti(eventoRequest.getMaxUtenti());

        return eventoRepository.save(e);
    }

    public void deleteEvento(int id) throws NotFoundException {
        Evento e = getEventoById(id);
        eventoRepository.delete(e);
    }

    public Evento assegnaUtente(int idEvento, int idUtente) throws NotFoundException{

        Utente utente = utenteService.getUtenteById(idUtente);
        Evento evento = getEventoById(idEvento);

        if(evento.getMaxUtenti() <= evento.getUtenti().size()){

            throw new OutOfAvailabilityException("L'evento è pieno");

        }
        if(utente.getId() != idUtente){

            throw new DifferentUserException("Non puoi registrare un'altro utente ad un evento");

        }
        if(evento.getUtenti().contains(utente)){

            throw new AlreadyAssignedException("Sei già iscritto a questo evento");

        }
        evento.setUtente(utente);
        return eventoRepository.save(evento);

    }

}
