package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {

    private Integer idCategoria;

    // Validación condicional: Solo requerido si idCategoria es nulo
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100, message = "El nombre de la categoría no puede exceder los 100 caracteres")
    private String nombreCategoria;

    /**
     * Default constructor for the CategoriaDTO class.
     * Initializes a new instance of the CategoriaDTO class without setting any properties.
     */
    public CategoriaDTO() {}

    /**
     * Constructor que inicializa el objeto CategoriaDTO con los valores especificados.
     *
     * @param idCategoria el identificador único de la categoría, puede ser nulo
     * @param nombreCategoria el nombre de la categoría, no debe estar en blanco ni exceder los 100 caracteres
     */
    public CategoriaDTO(Integer idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    // Getters y Setters
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Método para validar si los datos proporcionados son válidos.
     * @return true si es válido, false de lo contrario.
     */
    public boolean isValid() {
        // Si el ID no está presente, el nombre debe ser obligatorio
        return idCategoria != null || (nombreCategoria != null && !nombreCategoria.isBlank());
    }
}