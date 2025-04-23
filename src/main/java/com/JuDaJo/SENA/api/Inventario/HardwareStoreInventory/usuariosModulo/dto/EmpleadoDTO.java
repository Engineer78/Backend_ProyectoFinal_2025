package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

public class EmpleadoDTO {

    /**
     * Se declaran los atributos de la clase EmpleadoDTO.
     */
    private int idEmpleado;
    private String numeroDocumento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefonoMovil;
    private String direccionResidencia;
    private String contactoEmergencia;
    private String telefonoContacto;
    private int idUsuario; //relación
    private int idRol; // consulta y actualización
    private String nombreUsuario; //mostrar en consulta

    /**
     * Se crea el constructor vacío de la clase EmpleadoDTO.
     */
    public EmpleadoDTO() {}

    /**
     * Se crea el constructor con argumentos de la clase EmpleadoDTO.
     */
    public EmpleadoDTO(int idEmpleado, String numeroDocumento, String nombres, String apellidoPaterno,
                       String apellidoMaterno, String telefonoMovil, String direccionResidencia,
                       String contactoEmergencia, String telefonoContacto, String nombreUsuario,
                       String contraseña) {
        this.idEmpleado = idEmpleado;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefonoMovil = telefonoMovil;
        this.direccionResidencia = direccionResidencia;
        this.contactoEmergencia = contactoEmergencia;
        this.telefonoContacto = telefonoContacto;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    /**
     * Se crean los getters y setters de la clase EmpleadoDTO.
     */
    public int getIdEmpleado() {
        return idEmpleado;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
