package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public class UsuarioDTO {
    // Validaciones
    @Email(message = "El nombre de usuario debe tener formato de correo válido")
    @NotEmpty(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50, message = "El nombre de usuario no debe tener más de 50 caracteres")
    private String nombre;
    @NotEmpty(message = "La contraseña del usuario no puede estar vacia")
    @Size(min=6, message = "La contraseña debe tener como minimo 6 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/]).+$",
            message = "La contraseña debe contener al menos una letra mayúscula y un carácter especial"
    )
    private String contrasena;

    @NotNull( message="Debe seleccionar el rol")
    private Integer rol;

    //Constructor
    public UsuarioDTO() {}

    //Constructor con argumentos
    public UsuarioDTO(String nombre, String constrasena, Integer rol) {
        this.nombre = nombre;
        this.contrasena = constrasena;
        this.rol = rol;
    }
    //Getters And Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String constrasena) {
        this.contrasena = contrasena;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }
}
