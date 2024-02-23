package it.epicode.w7d5.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoRequest {

    @NotNull(message = "Campo obbligatorio")
    @NotEmpty(message = "Campo vuoto")
    private String nomeEvento;
    @NotNull(message = "Campo obbligatorio")
    @NotEmpty(message = "Campo vuoto")
    private String descrizione;
    @NotNull(message = "Campo obbligatorio")
    private LocalDate data;
    @NotNull(message = "Campo obbligatorio")
    @NotEmpty(message = "Campo vuoto")
    private String location;
    @NotNull(message = "Campo obbligatorio")
    private int maxUtenti;

}
