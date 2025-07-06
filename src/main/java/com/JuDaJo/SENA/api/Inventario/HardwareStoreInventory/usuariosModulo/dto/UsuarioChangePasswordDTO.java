package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public class UsuarioChangePasswordDTO {
    @NotBlank(message = "La contraseña actual es obligatoria")
    private String actual;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    private String nueva;

    @NotBlank(message = "Debe confirmar la nueva contraseña")
    private String confirmar;


    public UsuarioChangePasswordDTO() {}

    public UsuarioChangePasswordDTO(String actual, String nueva, String confirmar) {
        this.actual = actual;
        this.nueva = nueva;
        this.confirmar = confirmar;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getNueva() {
        return nueva;
    }

    public void setNueva(String nueva) {
        this.nueva = nueva;
    }

    public String getConfirmar() {
        return confirmar;
    }

    public void setConfirmar(String confirmar) {
        this.confirmar = confirmar;
    }

    // ✅ Validación cruzada
    @AssertTrue(message = "La nueva contraseña y su confirmación no coinciden")
    public boolean isNuevaCoincideConConfirmacion() {
        if (nueva == null || confirmar == null) return true; // deja que @NotBlank lo controle
        return nueva.equals(confirmar);
    }
}
