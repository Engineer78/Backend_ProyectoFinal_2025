package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.TipoDocumentoDTO;
import java.util.List;

public interface TipoDocumentoService {

    /**
     * Lista todos los tipos de documento.
     */
    List<TipoDocumentoDTO> listarTiposDocumento();

    /**
     * Crea un nuevo tipo de documento.
     */
    TipoDocumentoDTO crearTipoDocumento(TipoDocumentoDTO tipoDocumentoDTO);

    /**
     * Busca un tipo de documento por ID.
     */
    TipoDocumentoDTO obtenerTipoDocumentoPorId(Integer id);

    /**
     * Actualiza un tipo de documento existente.
     */
    TipoDocumentoDTO actualizarTipoDocumento(Integer id, TipoDocumentoDTO tipoDocumentoDTO);

    /**
     * Elimina un tipo de documento por ID.
     */
    void eliminarTipoDocumento(Integer id);

}
