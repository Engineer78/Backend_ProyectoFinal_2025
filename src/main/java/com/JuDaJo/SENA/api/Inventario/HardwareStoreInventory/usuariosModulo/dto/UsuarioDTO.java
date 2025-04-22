package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder
public class UsuarioDTO {
    // Validaciones
    @NotEmpty(message = "El nombre del usuario no puede ser nulo")
    @Size(min =3, max =50, message="El nombre debe estar entre 3 y 50 caracteres")
    private String nombre;
    @NotEmpty(message = "La contraseña del usuario no puede estar vacia")
    @Size(min=6, message = "La contraseña debe tener como minimo 6 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/]).+$",
            message = "La contraseña debe contener al menos una letra mayúscula y un carácter especial"
    )
    private String constraseña;
    @NotNull( message="Debe seleccionar el rol")
    private Integer rol;
}
