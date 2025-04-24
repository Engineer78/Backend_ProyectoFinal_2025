package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.TipoDocumentoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.TipoDocumento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.TipoDocumentoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    /**
     * Inyectar dependencia de TipoDocumentoRepository
     */
    private final TipoDocumentoRepository tipoDocumentoRepository;

    /**
     * Se inyecta la dependencia TipoDocumentoRepository en el constructor de la clase.
     * @param tipoDocumentoRepository
     */
    @Autowired
    public TipoDocumentoServiceImpl(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    /**
     * Crea un nuevo tipo de documento en la base de datos a partir de un objeto TipoDocumentoDTO.
     * @return
     */
    @Override
    public List<TipoDocumentoDTO> listarTiposDocumento() {
        return tipoDocumentoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un tipo de documento por su ID.
     * @param dto
     * @return
     */
    @Override
    public TipoDocumentoDTO crearTipoDocumento(TipoDocumentoDTO dto) {
        TipoDocumento tipo = new TipoDocumento();
        tipo.setCodigo(dto.getCodigo());
        tipo.setNombre(dto.getNombre());

        TipoDocumento guardado = tipoDocumentoRepository.save(tipo);
        return toDTO(guardado);
    }

    /**
     * Actualiza un tipo de documento existente.
     * @param id
     * @return
     */
    @Override
    public TipoDocumentoDTO obtenerTipoDocumentoPorId(Integer id) {
        TipoDocumento tipo = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de documento no encontrado con ID: " + id));
        return toDTO(tipo);
    }

    /**
     * Elimina un tipo de documento por su ID.
     * @param id
     */
    @Override
    public void eliminarTipoDocumento(Integer id) {
        if (!tipoDocumentoRepository.existsById(id)) {
            throw new NoSuchElementException("Tipo de documento no encontrado con ID: " + id);
        }
        tipoDocumentoRepository.deleteById(id);
    }

}
