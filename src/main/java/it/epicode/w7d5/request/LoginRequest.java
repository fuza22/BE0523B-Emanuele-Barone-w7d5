package it.epicode.w7d5.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "email obbligatoria")
    private String email;
    @NotBlank(message = "password obbligatoria")
    private String password;
}
