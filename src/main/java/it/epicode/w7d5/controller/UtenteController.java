package it.epicode.w7d5.controller;

import it.epicode.w7d5.exception.BadRequestException;
import it.epicode.w7d5.exception.CustomResponse;
import it.epicode.w7d5.exception.NotFoundException;
import it.epicode.w7d5.model.Utente;
import it.epicode.w7d5.request.UtenteRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/search")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){

        try{

            return CustomResponse.success(HttpStatus.OK.toString(), utenteService.getAll(pageable), HttpStatus.OK);

        }catch(Exception e){

            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<CustomResponse> getUtenteById(@PathVariable int id){

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), utenteService.getUtenteById(id), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse> saveUtente(@RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), utenteService.saveUtente(utenteRequest), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateUtente(@PathVariable int id, @RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), utenteService.updateUtente(id, utenteRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteUtente(@PathVariable int id){

        try {
            utenteService.deleteUtenteById(id);
            return CustomResponse.emptyResponse("L'utente con id = " + id + " è stato cancellato", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("/role/{email}")
    public Utente changeRole(@PathVariable String email, @RequestBody String role){

        return utenteService.updateRoleUtente(email, role);

    }

}
