package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.TipoDocumentoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.TipoDocumentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con los tipos de documento.
 * Este controlador proporciona endpoints para realizar las operaciones CRUD
 * (Crear, Leer, Actualizar, Eliminar) sobre los tipos de documento.
 */
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

    /**
     * Realiza la búsqueda por el ID del tipo de documento.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumentoDTO> obtenerPorId(@PathVariable Integer id) {
        TipoDocumentoDTO dto = tipoDocumentoService.obtenerTipoDocumentoPorId(id);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Realiza la creación de un nuevo tipo de documento en la base de datos.
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<TipoDocumentoDTO> crearTipoDocumento(@Valid @RequestBody TipoDocumentoDTO dto) {
        TipoDocumentoDTO creado = tipoDocumentoService.crearTipoDocumento(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    /**
     * Realiza la actualización de un tipo de documento dentro de la base de datos-
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumentoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody TipoDocumentoDTO dto) {
        TipoDocumentoDTO actualizado = tipoDocumentoService.actualizarTipoDocumento(id, dto);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    /**
     * Realiza la eliminación de un tipo de documento dentro de la base de datos.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        tipoDocumentoService.eliminarTipoDocumento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
