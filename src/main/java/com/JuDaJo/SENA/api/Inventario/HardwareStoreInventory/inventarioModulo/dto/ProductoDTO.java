package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.Producto;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO (Data Transfer Object) para la entidad Producto.
 * Este DTO se utiliza para transferir datos de la entidad Producto entre la capa de servicio y la capa de presentación.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductoDTO {
}
