package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

public class TipoDocumentoDTO {

    /**
     * Atributos de la clase TipoDocumentoDTO.
     */
    private Integer idTipoDocumento;
    private String codigo;
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
