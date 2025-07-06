package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.backup.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.backup.service.BackupService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BackupServiceImpl implements BackupService {

    /**
     * Ruta al ejecutable `mysqldump` usado para las operaciones de respaldo de la base de datos.
     * Se inyecta desde la configuración de la aplicación usando la clave `backup.mysqldump.path`.
     */
    @Value("${backup.mysqldump.path}")
    private String rutaMysqldump;

    /**
     * Carpeta de destino donde se almacenarán los archivos de respaldo generados.
     * Esta ruta se inyecta desde la configuración de la aplicación mediante la clave `backup.output.folder`.
     */
    @Value("${backup.output.folder}")
    private String carpetaBackup;

    /**
     * Nombre de usuario utilizado para autenticar la conexión con la base de datos.
     * Este valor se inyecta desde las propiedades de configuración de Spring
     * mediante la clave `spring.datasource.username`.
     */
    @Value("${spring.datasource.username}")
    private String usuario;

    /**
     * Contraseña asociada al usuario utilizado para autenticar la conexión con la base de datos.
     * Este valor se inyecta desde las propiedades de configuración de Spring
     * mediante la clave `spring.datasource.password`.
     */
    @Value("${spring.datasource.password}")
    private String contraseña;

    /**
     * URL de conexión a la base de datos usada por la aplicación.
     * Este valor se inyecta desde las propiedades de configuración de Spring
     * utilizando la clave `spring.datasource.url`.
     */
    @Value("${spring.datasource.url}")
    private String urlBD;

    /**
     * Representa la fuente de datos utilizada para gestionar las conexiones a la base de datos en la aplicación.
     * Es inyectada automáticamente por Spring para facilitar las operaciones relacionadas con la base de datos.
     *
     * Se utiliza internamente en la clase {@code BackupServiceImpl} para realizar exportaciones
     * e importaciones de backups de la base de datos.
     */
    @Autowired
    private DataSource dataSource;

    /**
     * Servicio para gestionar los movimientos de inventario.
     * Este bean se inyecta automáticamente y es utilizado dentro de la clase {@code BackupServiceImpl}
     * para registrar o manipular información relacionada con los movimientos de inventario cuando se
     * exportan o importan backups de la base de datos.
     *
     * Relacionado con la lógica de operaciones de inventario, este servicio se encarga de registrar
     * movimientos, listar movimientos existentes, y listar movimientos filtrados por entidad afectada.
     */
    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    /**
     * Entorno de configuración de la aplicación proporcionado por Spring.
     * Esta instancia permite acceder a las propiedades de configuración disponibles
     * en el archivo de propiedades o en variables de entorno.
     *
     * En el contexto de la clase {@code BackupServiceImpl}, se utiliza para obtener información relevante
     * como la ruta al ejecutable `mysql.exe` configurada en la clave `backup.mysql.path` durante el
     * proceso de importación de respaldos.
     *
     * El bean es inyectado automáticamente por el contenedor de Spring mediante la anotación {@code @Autowired}.
     */
    @Autowired
    private Environment environment;


    /**
     * Realiza la operación de exportar un backup de la base de datos utilizando el comando mysqldump.
     * El sistema genera un archivo SQL que contiene el respaldo completo de la base de datos.
     *
     * Procesos principales:
     * - Extraer el nombre de la base de datos desde la URL de conexión.
     * - Crear una carpeta destino para almacenar el archivo de respaldo, organizada por fecha.
     * - Construir y ejecutar el comando mysqldump con las credenciales de la base de datos.
     * - Almacenar el archivo generado en la ubicación de destino configurada.
     *
     * @return La ruta completa del archivo SQL generado que contiene el respaldo de la base de datos.
     * @throws Exception Si ocurre un error durante el proceso de exportación, como rutas incorrectas de mysqldump
     *                   o credenciales de base de datos inválidas.
     */
    @Override
    public String exportarBackup() throws Exception {
        // Extraer nombre de BD desde la URL
        String nombreBD = extraerNombreBaseDeDatos(urlBD);
        int puerto = extraerPuertoDesdeURL(urlBD);

        // Crear carpeta destino por fecha
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String carpetaDestino = carpetaBackup + "\\" + fecha;
        File carpeta = new File(carpetaDestino);
        if (!carpeta.exists()) carpeta.mkdirs();

        String rutaArchivoBackup = carpetaDestino + "\\backup.sql";

        // Comando mysqldump con puerto correcto
        String comando = String.format(
                "\"%s\" -u%s -p%s --port=%d --add-drop-table --databases %s -r \"%s\"",
                rutaMysqldump, usuario, contraseña, puerto, nombreBD, rutaArchivoBackup
        );

        System.out.println("🛠 Comando generado: " + comando);

        // Ejecutar el comando
        Process proceso = Runtime.getRuntime().exec(comando);
        int resultado = proceso.waitFor();

        if (resultado == 0) {
            return rutaArchivoBackup;
        } else {
            throw new Exception("❌ Error generando el backup. Verifica la ruta de mysqldump o credenciales.");
        }
    }

    /**
     * Extrae el nombre de la base de datos desde una URL de conexión proporcionada.
     *
     * @param url La URL de conexión a la base de datos en formato JDBC.
     *            Por ejemplo: jdbc:mysql://localhost:3307/nombreBaseDeDatos.
     * @return El nombre de la base de datos extraído desde la URL.
     *         Si la URL es inválida o no contiene un nombre de base de datos,
     *         el comportamiento del método podría no ser predecible.
     */
    private String extraerNombreBaseDeDatos(String url) {
        // jdbc:mysql://localhost:3307/hardware_store_inventory_api
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * Extracts the port number from a given MySQL JDBC URL.
     * If an error occurs during extraction, it returns the default port 3307.
     *
     * @param url the MySQL JDBC URL as a string, typically starting with "jdbc:mysql://"
     * @return the extracted port number as an integer, or 3307 if extraction fails
     */
    private int extraerPuertoDesdeURL(String url) {
        try {
            String sinPrefijo = url.replace("jdbc:mysql://", "");
            String parteHost = sinPrefijo.split("/")[0]; // localhost:3307
            String[] hostYPuerto = parteHost.split(":");
            return Integer.parseInt(hostYPuerto[1]);
        } catch (Exception e) {
            return 3307; // Valor por defecto
        }
    }

    /**
     * Método para importar un respaldo de base de datos desde un archivo SQL.
     *
     * @param token            Token de seguridad utilizado para la autenticación.
     * @param archivoSql       Archivo SQL que contiene el respaldo de la base de datos.
     * @return Un mensaje indicando si la importación fue exitosa.
     * @throws Exception       Si ocurre un error durante el proceso de importación.
     */
    @Override
    public String importarBackup(String token, MultipartFile archivoSql) throws Exception {
        try {
            // 1. Guardar temporalmente el archivo recibido
            File tempFile = File.createTempFile("backup", ".sql");
            archivoSql.transferTo(tempFile);

            // 2. Extraer datos desde la URL
            String nombreBD = extraerNombreBaseDeDatos(urlBD);
            int puerto = extraerPuertoDesdeURL(urlBD);

            // 3. Obtener ruta del ejecutable mysql.exe desde application.properties
            String mysqlPath = environment.getProperty("backup.mysql.path");

            // 4. Crear comando con la ruta completa y redireccionar la entrada del archivo .sql
            ProcessBuilder builder = new ProcessBuilder(
                    mysqlPath,
                    "-u", usuario,
                    "-p" + contraseña,
                    "-P", String.valueOf(puerto),
                    nombreBD
            );
            builder.redirectInput(tempFile);

            // 5. Ejecutar el proceso
            Process process = builder.start();
            int exitCode = process.waitFor();


            // 6. Validar resultado
            if (exitCode != 0) {
                throw new RuntimeException("Error al importar el respaldo. Código de salida: " + exitCode);
            }

            // 7. Eliminar archivo temporal
            //tempFile.delete();

            return "Importación exitosa.";
        } catch (Exception e) {
            e.printStackTrace();  // Imprime el stack trace para depuración
            throw new RuntimeException("Error al importar el backup: " + e.getMessage());
        }
    }
}
