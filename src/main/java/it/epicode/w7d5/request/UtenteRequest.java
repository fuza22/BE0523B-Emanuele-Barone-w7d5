package it.epicode.w7d5.request;

import it.epicode.w7d5.enums.Ruolo;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UtenteRequest {

    @NotNull(message = "Campo obbligatorio")
    @NotEmpty(message = "Campo vuoto")
    public String nome;
    @NotNull(message = "Campo obbligatorio")
    @NotEmpty(message = "Campo vuoto")
    private String cognome;
    @Email(message = "Inserire una mail valida")
    @NotEmpty(message = "Campo vuoto")
    private String email;
    @NotNull(message = "Campo obbligatorio")
    @NotEmpty(message = "Campo vuoto")
    private String password;
}
