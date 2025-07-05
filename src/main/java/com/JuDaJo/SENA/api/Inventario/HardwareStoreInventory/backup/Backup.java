package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.backup;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.backup.service.BackupService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.util.JwtUtil;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.Claims;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Controlador para gestionar backups manuales de la base de datos.
 */
@RestController
@RequestMapping("/api/backup")
@CrossOrigin(origins = "*") // opcional según configuración CORS
public class Backup {

    @Autowired
    private BackupService backupService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MovimientoInventarioService movimientoInventarioService;


    /**
     * Endpoint que genera un backup manual de la BD y devuelve la ruta.
     *
     * @return Ruta del archivo generado.
     */
    @GetMapping("/exportar")
    public ResponseEntity<Resource> exportarBackup(@RequestHeader("Authorization") String tokenHeader) {
        try {
            // 1. Validar encabezado
            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).build();
            }

            String token = tokenHeader.substring(7);
            Claims claims = jwtUtil.extractClaims(token);
            List<String> permisos = claims.get("permisos", List.class);

            if (permisos == null || !permisos.contains("EXPORTAR_BD")) {
                return ResponseEntity.status(403).build();
            }

            // 2. Ejecutar backup
            String ruta = backupService.exportarBackup();

            // 3. Leer archivo como recurso
            File archivo = new File(ruta);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(archivo));

            // 4. Registrar trazabilidad
            movimientoInventarioService.registrarMovimiento(
                    token,
                    "Backup",
                    TipoMovimiento.EXPORTAR.toString(),
                    null,
                    "Se exportó manualmente la base de datos"
            );

            // 5. Devolver archivo como descarga
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=backup.sql")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace(); // Para que aparezca en consola
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Importa un archivo de backup de la base de datos de manera manual.
     *
     * @param tokenHeader el encabezado de autorización con el token Bearer para validación.
     * @param archivo el archivo MultipartFile que contiene el backup a importar.
     * @return ResponseEntity con el estado de la operación y un mensaje o error según corresponda.
     */

    @PostMapping("/importar")
    public ResponseEntity<?> importarBackup(
            @RequestHeader("Authorization") String tokenHeader,
            @RequestParam("archivo") MultipartFile archivo) {

        try {
            // 1. Validar encabezado y extraer token
            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).build();
            }
            String token = tokenHeader.substring(7);

            // 2. Validar permiso desde claims
            Claims claims = jwtUtil.extractClaims(token);
            List<String> permisos = claims.get("permisos", List.class);
            if (permisos == null || !permisos.contains("IMPORTAR_BD")) {
                return ResponseEntity.status(403).build();
            }

            // 3. Ejecutar importación desde el servicio y ajustar trazabilidad
            backupService.importarBackup(token, archivo);

            movimientoInventarioService.registrarMovimiento(
                    token,
                    "Backup",
                    TipoMovimiento.IMPORTAR.toString(),
                    null,
                    "Se importó manualmente la base de datos"
            );

            // 4. Respuesta exitosa
            return ResponseEntity.ok("Backup importado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al importar el backup.");
        }
    }
}
