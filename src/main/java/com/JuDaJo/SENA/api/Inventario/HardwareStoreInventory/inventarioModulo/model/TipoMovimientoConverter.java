package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Clase convertidora para transformar entre el enum TipoMovimiento y su representación correspondiente en la base de datos.
 * Implementa la interfaz AttributeConverter de JPA para proporcionar conversión bidireccional.
 *
 * Esta clase es responsable de convertir un valor enum TipoMovimiento en una cadena de texto para su almacenamiento
 * en la base de datos y de convertir una cadena de texto de la base de datos de vuelta a un valor enum TipoMovimiento.
 * La conversión no es sensible a mayúsculas/minúsculas al transformar desde la base de datos a la representación enum.
 *
 * Al implementar este convertidor:
 * - La conversión de enum a base de datos almacenará el nombre del enum TipoMovimiento como una cadena de texto.
 * - La conversión de base de datos a enum espera que la cadena almacenada coincida con un valor válido del enum TipoMovimiento.
 *
 * La clase lanza una RuntimeException si se encuentra un valor inválido al convertir desde la base de datos al enum.
 *
 * Nota: Este convertidor no se aplica automáticamente. Debe ser referenciado o utilizado explícitamente en la definición de la entidad.
 */
@Converter(autoApply = false)
public class TipoMovimientoConverter implements AttributeConverter<TipoMovimiento, String> {

    /**
     * Convierte el valor enum TipoMovimiento especificado a su representación correspondiente en la columna
     * de la base de datos como una cadena de texto.
     * Si el TipoMovimiento proporcionado es null, este método retorna null.
     *
     * @param tipoMovimiento el valor enum TipoMovimiento que será convertido a una representación de columna
     *                       en la base de datos; puede ser null.
     * @return una cadena de texto que representa el nombre del TipoMovimiento en la base de datos,
     *         o null si la entrada es null.
     */
    @Override
    public String convertToDatabaseColumn(TipoMovimiento tipoMovimiento) {
        return tipoMovimiento != null ? tipoMovimiento.name() : null;
    }

    /**
     * Convierte un valor de cadena de texto proveniente de la base de datos en su correspondiente enumeración TipoMovimiento.
     * Si el valor proporcionado es null, retorna null. Lanza una RuntimeException si el valor no corresponde a ningún
     * elemento válido del enum TipoMovimiento.
     *
     * @param dbData la cadena de texto proveniente de la base de datos que será convertida al enum TipoMovimiento; puede ser null.
     * @return el valor del enum TipoMovimiento que corresponde a la cadena de texto proporcionada, o null si la entrada es null.
     * @throws RuntimeException si la cadena de texto no coincide con ningún valor válido del TipoMovimiento.
     */
    @Override
    public TipoMovimiento convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? TipoMovimiento.valueOf(dbData.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("❌ TipoMovimiento inválido: " + dbData);
        }
    }

}