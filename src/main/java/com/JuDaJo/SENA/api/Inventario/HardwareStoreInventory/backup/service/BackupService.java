package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.backup.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interfaz que define el contrato para realizar backups manuales.
 */
public interface BackupService {
    /**
     * Ejecuta un comando para generar un archivo .sql de backup de la base de datos.
     *
     * @return La ruta del archivo generado.
     * @throws Exception si hay un error durante el proceso.
     */
    String exportarBackup() throws Exception;

    /**
     * Importa un archivo SQL de backup a la base de datos utilizando un token de autorización
     * para validar el proceso.
     *
     * @param token el token de autorización utilizado para validar la operación de importación.
     * @param archivoSql el archivo SQL del tipo MultipartFile que contiene el backup a importar.
     * @return un mensaje indicando el resultado de la operación de importación.
     * @throws Exception si ocurre un error durante el proceso de importación.
     */
    String importarBackup(String token, MultipartFile archivoSql) throws Exception;

}