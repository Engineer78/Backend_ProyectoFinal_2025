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
}
