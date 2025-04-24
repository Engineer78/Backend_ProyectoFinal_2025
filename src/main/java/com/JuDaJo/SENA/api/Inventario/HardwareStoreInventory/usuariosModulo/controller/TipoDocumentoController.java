package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.TipoDocumentoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-documento")
public class TipoDocumentoController {

    /**
     * Servicio que gestiona las operaciones relacionadas con los tipos de documento.
     * Este campo es inyectado a través del constructor usando @Autowired para mantener
     * los principios de inversión de dependencias.
     */
    private final TipoDocumentoService tipoDocumentoService;

    @Autowired
    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    /**
     * Realiza la búsqueda de todos los tipos de documentos en la base de datos.
     * @return
     */
    @GetMapping
    public ResponseEntity<List<TipoDocumentoDTO>> listarTiposDocumento() {
        List<TipoDocumentoDTO> lista = tipoDocumentoService.listarTiposDocumento();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }




}
