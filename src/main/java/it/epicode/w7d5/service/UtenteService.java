package it.epicode.w7d5.service;

import it.epicode.w7d5.enums.Ruolo;
import it.epicode.w7d5.exception.NotFoundException;
import it.epicode.w7d5.model.Utente;
import it.epicode.w7d5.repository.UtenteRepository;
import it.epicode.w7d5.request.UtenteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Page<Utente> getAll(Pageable pageable){

        return utenteRepository.findAll(pageable);

    }

    public Utente getUtenteById(int id) throws NotFoundException {
        return utenteRepository.findById(id).orElseThrow(()->new NotFoundException("Utente con id = " + id + " non trovato"));
    }

    public Utente getUtenteByEmail(String email){

        return utenteRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Email non trovata"));

    }

    public Utente saveUtente(UtenteRequest utenteRequest){
        Utente u = new Utente();
        u.setNome(utenteRequest.getNome());
        u.setCognome(utenteRequest.getCognome());
        u.setEmail(utenteRequest.getEmail());
        u.setPassword(encoder.encode(utenteRequest.getPassword()));
        u.setRuolo(Ruolo.UTENTE);

        return utenteRepository.save(u);
    }

    public Utente updateUtente(int id, UtenteRequest utenteRequest) throws NotFoundException {
        Utente u = getUtenteById(id);
        u.setNome(utenteRequest.getNome());
        u.setCognome(utenteRequest.getCognome());
        u.setEmail(utenteRequest.getEmail());
        u.setEmail(utenteRequest.getEmail());
        u.setPassword(encoder.encode(utenteRequest.getPassword()));


        return utenteRepository.save(u);
    }

    public void deleteUtenteById(int id) throws NotFoundException {
        Utente u = getUtenteById(id);
        utenteRepository.delete(u);
    }

    public void deleteUtenteByEmail(String email) throws NotFoundException {
        utenteRepository.deleteByEmail(email);
    }

    public Utente updateRoleUtente(String email, String role){
        Utente u = getUtenteByEmail(email);
        u.setRuolo(Ruolo.valueOf(role));
        return utenteRepository.save(u);

    }

}
