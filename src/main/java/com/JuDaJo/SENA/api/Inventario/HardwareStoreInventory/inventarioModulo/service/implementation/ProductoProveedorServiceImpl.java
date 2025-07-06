package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.ProductoProveedorDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Producto;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.ProductoProveedor;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Proveedor;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository.ProductoProveedorRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository.ProductoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository.ProveedorRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.ProductoProveedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de ProductoProveedor.
 * Se encarga de la lógica para crear, actualizar y eliminar relaciones entre productos y proveedores.
 */
@Service
public class ProductoProveedorServiceImpl implements ProductoProveedorService {

    private final ProductoProveedorRepository productoProveedorRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;

    public ProductoProveedorServiceImpl(ProductoProveedorRepository productoProveedorRepository,
                                        ProductoRepository productoRepository,
                                        ProveedorRepository proveedorRepository) {
        this.productoProveedorRepository = productoProveedorRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    /**
     * Lista todas las relaciones producto-proveedor existentes.
     */
    @Override
    public List<ProductoProveedorDTO> listarTodos() {
        return productoProveedorRepository.findAll().stream()
                .map(pp -> new ProductoProveedorDTO(
                        pp.getIdProductoProveedor(),
                        pp.getProducto().getIdProducto(),
                        pp.getProveedor().getIdProveedor(),
                        pp.getPrecioCompra()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva relación producto-proveedor.
     */
    @Override
    public ResponseEntity<ProductoProveedorDTO> crearRelacion(ProductoProveedorDTO dto) {
        Optional<Producto> productoOpt = productoRepository.findById(dto.getIdProducto());
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(dto.getIdProveedor());

        if (productoOpt.isEmpty() || proveedorOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 🔐 Validación personalizada: evitar relación duplicada
        Optional<ProductoProveedor> existente = productoProveedorRepository
                .findByProductoIdProductoAndProveedorIdProveedor(dto.getIdProducto(), dto.getIdProveedor());

        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null); // Ya existe la relación, conflicto 409
        }


        ProductoProveedor pp = new ProductoProveedor();
        pp.setProducto(productoOpt.get());
        pp.setProveedor(proveedorOpt.get());
        pp.setPrecioCompra(dto.getPrecioCompra());

        ProductoProveedor guardado = productoProveedorRepository.save(pp);

        return new ResponseEntity<>(
                new ProductoProveedorDTO(
                        guardado.getIdProductoProveedor(),
                        guardado.getProducto().getIdProducto(),
                        guardado.getProveedor().getIdProveedor(),
                        guardado.getPrecioCompra()
                ), HttpStatus.CREATED);
    }

    /**
     * Actualiza una relación producto-proveedor existente.
     */
    @Override
    public ResponseEntity<ProductoProveedorDTO> actualizarRelacion(int id, ProductoProveedorDTO dto) {
        return productoProveedorRepository.findById(id)
                .map(pp -> {
                    Producto producto = productoRepository.findById(dto.getIdProducto())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                    Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

                    pp.setProducto(producto);
                    pp.setProveedor(proveedor);
                    pp.setPrecioCompra(dto.getPrecioCompra());

                    ProductoProveedor actualizado = productoProveedorRepository.save(pp);

                    return ResponseEntity.ok(new ProductoProveedorDTO(
                            actualizado.getIdProductoProveedor(),
                            actualizado.getProducto().getIdProducto(),
                            actualizado.getProveedor().getIdProveedor(),
                            actualizado.getPrecioCompra()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina una relación producto-proveedor por su ID.
     */
    @Override
    public ResponseEntity<Void> eliminarRelacion(int id) {
        if (productoProveedorRepository.existsById(id)) {
            productoProveedorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
