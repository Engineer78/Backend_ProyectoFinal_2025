package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Clase DTO para la transferencia de datos de un empleado.
 * Esta clase contiene los datos básicos de un empleado y se utiliza
 * para encapsular y transferir información entre capas de la aplicación.
 */
public class EmpleadoDTO {

    /**
     * Se declaran los atributos de la clase EmpleadoDTO.
     */
    private int idEmpleado;
    private int idUsuario; //relación
    private int idRol; // consulta y actualización
    private String numeroDocumento;
    private Integer idTipoDocumento;
    private String nombreTipoDocumento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefonoMovil;
    private String direccionResidencia;
    private String contactoEmergencia;
    private String telefonoContacto;
    private String nombreUsuario; //mostrar en consulta
    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Size(min = 8, max = 16, message = "La contraseña debe tener entre 8 y 50 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\-_.])[A-Za-z\\d@$!%*?&\\-_.]{8,}$",
            message = "Debe incluir mayúsculas, minúsculas, números y un carácter especial"
    )
    private String contrasena;
    private String nombreRol;


    /**
     * Se crea el constructor vacío de la clase EmpleadoDTO.
     */
    public EmpleadoDTO() {}

    /**
     * Se crea el constructor con argumentos de la clase EmpleadoDTO.
     */
    public EmpleadoDTO(int idEmpleado, int idUsuario, int idRol, String numeroDocumento, Integer idTipoDocumento,
                       String nombreTipoDocumento, String nombres, String apellidoPaterno,
                       String apellidoMaterno, String telefonoMovil, String direccionResidencia,
                       String contactoEmergencia, String telefonoContacto,
                       String nombreUsuario, String contrasena, String nombreRol) {
        this.idEmpleado = idEmpleado;
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.numeroDocumento = numeroDocumento;
        this.idTipoDocumento = idTipoDocumento;
        this.nombreTipoDocumento = nombreTipoDocumento;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefonoMovil = telefonoMovil;
        this.direccionResidencia = direccionResidencia;
        this.contactoEmergencia = contactoEmergencia;
        this.telefonoContacto = telefonoContacto;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreRol = nombreRol;

    }

    /**
     * Se crean los getters y setters de la clase EmpleadoDTO.
     */
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
