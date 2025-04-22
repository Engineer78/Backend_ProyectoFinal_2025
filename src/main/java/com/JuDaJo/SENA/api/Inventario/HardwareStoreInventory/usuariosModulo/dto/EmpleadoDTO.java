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
    private int idUsuario; //Se usa solo ID, no todo el objeto usuario.

    /**
     * Se crea el constructor vacío de la clase EmpleadoDTO.
     */
    public EmpleadoDTO() {}

    /**
     * Se crea el constructor con argumentos de la clase EmpleadoDTO.
     */
    public EmpleadoDTO(int idEmpleado, String numeroDocumento, String nombres, String apellidoPaterno, String apellidoMaterno, String telefonoMovil, String direccionResidencia, String contactoEmergencia, String telefonoContacto, int idUsuario) {
        this.idEmpleado = idEmpleado;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefonoMovil = telefonoMovil;
        this.direccionResidencia = direccionResidencia;
        this.contactoEmergencia = contactoEmergencia;
    }

}
