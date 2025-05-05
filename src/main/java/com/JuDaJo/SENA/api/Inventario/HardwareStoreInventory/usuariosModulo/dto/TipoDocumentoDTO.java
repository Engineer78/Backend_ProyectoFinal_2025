package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TipoDocumentoDTO {

    /**
     * Atributos de la clase TipoDocumentoDTO.
     */
    private Integer idTipoDocumento;

    @NotBlank(message = "El código del tipo de documento no puede estar en blanco")
    @Size(max = 5, message = "El código no debe tener más de 5 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre del tipo de documento no puede estar en blanco")
    @Size(max = 50, message = "El nombre no debe tener más de 50 caracteres")
    private String nombre;

    /**
     * Contructor vacío de la clase TipoDocumentoDTO.
     */
    public TipoDocumentoDTO() {
    }

    /**
     * Se crea un constructor con los atributos de la clase.
     * @param idTipoDocumento
     * @param codigo
     * @param nombre
     */
    public TipoDocumentoDTO(Integer idTipoDocumento, String codigo, String nombre) {
        this.idTipoDocumento = idTipoDocumento;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /**
     * Se crean los getters y setters para los atributos de la clase.
     * @return
     */
    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
