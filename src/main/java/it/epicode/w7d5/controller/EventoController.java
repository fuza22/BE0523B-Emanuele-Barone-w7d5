package it.epicode.w7d5.controller;

import it.epicode.w7d5.exception.BadRequestException;
import it.epicode.w7d5.exception.CustomResponse;
import it.epicode.w7d5.exception.NotFoundException;
import it.epicode.w7d5.model.Evento;
import it.epicode.w7d5.model.Utente;
import it.epicode.w7d5.request.EventoRequest;
import it.epicode.w7d5.request.UtenteRequest;
import it.epicode.w7d5.service.EventoService;
import it.epicode.w7d5.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/search")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){

        try{

            return CustomResponse.success(HttpStatus.OK.toString(), eventoService.getAll(pageable), HttpStatus.OK);

        }catch(Exception e){

            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<CustomResponse> getEventoById(@PathVariable int id){

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), eventoService.getEventoById(id), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse> saveEvento(@RequestBody @Validated EventoRequest eventoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), eventoService.saveEvento(eventoRequest), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateEvento(@PathVariable int id, @RequestBody @Validated EventoRequest eventoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), eventoService.updateEvento(id, eventoRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteEvento(@PathVariable int id){

        try {
            eventoService.deleteEvento(id);
            return CustomResponse.emptyResponse("L'evento con id = " + id + " è stato cancellato", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<CustomResponse> setUtente(@PathVariable int id, @RequestParam int idUtente){
        try {
            Evento e = eventoService.assegnaUtente(idUtente, id);
            return CustomResponse.success(HttpStatus.OK.toString(), e, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
