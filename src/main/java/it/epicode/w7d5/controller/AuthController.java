package it.epicode.w7d5.controller;


import it.epicode.w7d5.exception.BadRequestException;
import it.epicode.w7d5.exception.LoginFaultException;
import it.epicode.w7d5.model.Utente;
import it.epicode.w7d5.request.LoginRequest;
import it.epicode.w7d5.request.UtenteRequest;
import it.epicode.w7d5.security.JwtTools;
import it.epicode.w7d5.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public Utente register(@RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            throw new BadRequestException(bindingResult.getAllErrors().toString());

        }

        return utenteService.saveUtente(utenteRequest);

    }

    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        Utente utente = utenteService.getUtenteByEmail(loginRequest.getEmail());

        if(encoder.matches(loginRequest.getPassword(), utente.getPassword())){
            return jwtTools.createToken(utente);
        }
        else{
            throw new LoginFaultException("username/password errate");
        }

    }
}
