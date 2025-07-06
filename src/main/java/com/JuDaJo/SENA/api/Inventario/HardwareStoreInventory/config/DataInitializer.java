package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.config;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.*;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Permiso;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.RolPermiso;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PermisoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolPermisoRepository;


import java.util.List;

/**
 * Clase de configuración para inicializar datos básicos del sistema.
 * Esta clase se encarga de crear los datos iniciales necesarios para el funcionamiento
 * del sistema cuando la base de datos está vacía, incluyendo:
 * - Usuario administrador
 * - Perfil administrativo
 * - Rol administrativo
 * - Tipo de documento
 * - Empleado administrador
 */
@Configuration
public class DataInitializer {

    /**
     * Crea un Bean que implementa CommandLineRunner para ejecutar la inicialización de datos.
     * Este método se ejecutará automáticamente al iniciar la aplicación.
     *
     * @param perfilRepository Repositorio para gestionar perfiles
     * @param rolRepository Repositorio para gestionar roles
     * @param tipoDocumentoRepository Repositorio para gestionar tipos de documento
     * @param usuarioRepository Repositorio para gestionar usuarios
     * @param empleadoRepository Repositorio para gestionar empleados
     * @param passwordEncoder Codificador de contraseñas para seguridad
     * @return CommandLineRunner que ejecuta la inicialización de datos
     */

    @Bean
    public CommandLineRunner initData(
            PerfilRepository perfilRepository,
            RolRepository rolRepository,
            TipoDocumentoRepository tipoDocumentoRepository,
            EmpleadoRepository empleadoRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            PermisoRepository permisoRepository,
            RolPermisoRepository rolPermisoRepository
    ) {
        return args -> {
            // 1. Crear perfil solo si no existe
            if (perfilRepository.count() == 0) {
                Perfil perfilAdmin = new Perfil();
                perfilAdmin.setNombrePerfil("Administrador Global");
                perfilAdmin.setDescripcion("Acceso completo a todas las funcionalidades del sistema");
                perfilAdmin = perfilRepository.save(perfilAdmin);
            }

            // Obtener el perfil guardado (puede ser el primero si ya existe)
            Perfil perfilGuardado = perfilRepository.findAll().get(0);


            // 2. Crear rol asociado al perfil solo si no existe
            if (rolRepository.count() == 0) {
                Rol rolAdmin = new Rol();
                rolAdmin.setNombreRol("ADMIN");
                rolAdmin.setDescripcion("Rol con permisos administrativos totales");
                rolAdmin.setPerfil(perfilGuardado);
                rolAdmin = rolRepository.save(rolAdmin);
            }

            // Obtener el rol guardado
            Rol rolAdmin = rolRepository.findAll().get(0);

            // 3. Crear tipo de documento
            if (tipoDocumentoRepository.count() == 0) {
                TipoDocumento dni = new TipoDocumento();
                dni.setCodigo("CC");
                dni.setNombre("Cédula de Ciudadanía");
                tipoDocumentoRepository.save(dni);
            }

            // Obtener el tipo de documento guardado
            TipoDocumento dni = tipoDocumentoRepository.findAll().get(0);

            // 4. Crear usuario administrador + empleado completo solo si no existe
            if (!usuarioRepository.existsByNombreUsuario("admin@gmail.com")) {
                Usuario admin = new Usuario();
                admin.setNombreUsuario("admin@gmail.com");
                admin.setContrasena(passwordEncoder.encode("Admin12345@"));
                admin.setRol(rolAdmin); // Ya debe existir el rol

                Empleado empleadoAdmin = new Empleado();
                empleadoAdmin.setNumeroDocumento("12345678");
                empleadoAdmin.setTipoDocumento(dni); // ya obtenido arriba
                empleadoAdmin.setNombres("Joaquin");
                empleadoAdmin.setApellidoPaterno("Jimenez");
                empleadoAdmin.setApellidoMaterno("Rosas");
                empleadoAdmin.setTelefonoMovil("3108037930");
                empleadoAdmin.setDireccionResidencia("Calle 1 # 2-3");
                empleadoAdmin.setContactoEmergencia("Sistema");
                empleadoAdmin.setTelefonoContacto("3108037930");
                empleadoAdmin.setUsuario(admin); // 🔗 Aquí se asocia directamente sin guardar antes

                System.out.println("🟡 Hash generado: " + passwordEncoder.encode("Admin12345@"));
                System.out.println("🟡 Usuario a persistir: " + admin.getNombreUsuario());
                System.out.println("🟡 Contraseña (en hash): " + admin.getContrasena());

                empleadoRepository.save(empleadoAdmin); // Aquí se guarda el usuario también (cascade)

                System.out.println("✅ Usuario administrador creado correctamente: admin@gmail.com / admin12345@ (encriptado)");
            }

            // 5. Crear permisos si no existen
            if (permisoRepository.count() == 0) {
                permisoRepository.saveAll(List.of(
                        // Usuarios
                        new Permiso(null, "CREAR_USUARIO", "Permite registrar nuevos usuarios"),
                        new Permiso(null, "CONSULTAR_USUARIO", "Permite consultar la lista de usuarios"),
                        new Permiso(null, "ACTUALIZAR_USUARIO", "Permite actualizar la información de un usuario"),
                        new Permiso(null, "ELIMINAR_USUARIO", "Permite eliminar usuarios del sistema"),

                        // Tipo de Documento (modal)
                        new Permiso(null, "CREAR_TIPO_DOCUMENTO", "Permite crear nuevos tipos de documento"),
                        new Permiso(null, "CONSULTAR_TIPO_DOCUMENTO", "Permite consultar los tipos de documento existentes"),
                        new Permiso(null, "ACTUALIZAR_TIPO_DOCUMENTO", "Permite modificar tipos de documento"),

                        // Perfil (modal)
                        new Permiso(null, "CREAR_PERFIL", "Permite crear nuevos perfiles"),
                        new Permiso(null, "CONSULTAR_PERFIL", "Permite consultar perfiles existentes"),
                        new Permiso(null, "ACTUALIZAR_PERFIL", "Permite modificar perfiles"),

                        // Rol (modal)
                        new Permiso(null, "CREAR_ROL", "Permite crear nuevos roles"),
                        new Permiso(null, "CONSULTAR_ROL", "Permite consultar roles existentes"),
                        new Permiso(null, "ACTUALIZAR_ROL", "Permite modificar roles"),

                        // Inventario
                        new Permiso(null, "MODULO_USUARIOS", "Acceso al módulo de registro y gestión de usuarios"),
                        new Permiso(null, "MODULO_INVENTARIO", "Acceso al módulo de registro y gestión de inventario"),
                        new Permiso(null, "CREAR_PRODUCTO", "Permite registrar nuevos productos en el inventario"),
                        new Permiso(null, "CONSULTAR_PRODUCTO", "Permite consultar el inventario"),
                        new Permiso(null, "ACTUALIZAR_PRODUCTO", "Permite modificar productos del inventario"),
                        new Permiso(null, "ELIMINAR_PRODUCTO", "Permite eliminar productos del inventario"),

                        // Inventario extras
                        new Permiso(null, "EDITAR_PROVEEDOR", "Permite editar información del proveedor desde el inventario"),
                        new Permiso(null, "EDITAR_CATEGORIA", "Permite editar el nombre de la categoría desde el inventario"),

                        // Trazabilidad
                        new Permiso(null, "VER_MOVIMIENTOS", "Permite visualizar los movimientos registrados en el sistema"),

                        // Insignia
                        new Permiso(null, "VER_MI_PERFIL", "Permite visualizar las opciones del menú Mi perfil"),
                        new Permiso(null, "GESTIONAR_PERMISOS", "Permite abrir el modal para asignar permisos a los roles"),
                        new Permiso(null, "VER_PERMISOS_ASIGNADOS", "Permite ver qué acciones puede realizar el usuario"),
                        new Permiso(null, "VER_ACERCA_DE", "Permite ver el modal con los créditos y agradecimientos"),
                        new Permiso(null, "CAMBIAR_CONTRASENA", "Permite cambiar su contraseña desde la insignia"),
                        new Permiso(null, "VER_MANUALES", "Permite acceder a los manuales técnicos y de usuario"),
                        new Permiso(null, "MODULOS_INTEGRADOS", "Permite acceder a la documentación técnica de las apis"),
                        new Permiso(null, "PLAN_DE_PRUEBAS", "Permite acceder a las pruebas realizadas del software"),
                        new Permiso(null, "CASOS_y_AMBIENTE_PRUEBAS", "Permite acceder al diseño de casos y al ambiente de pruebas"),
                        new Permiso(null, "MANUAL_TECNICO_DEL_SISTEMA", "Permite acceder al manual técnico del sistema HARDWARE STORE Inventory"),
                        new Permiso(null, "MANUAL_DEL_USUARIO", "Permite acceder al manual del usuario"),

                        //BackupDB
                        // BackupDB
                        new Permiso(null, "GESTIONAR_BASE_DATOS", "Permite acceder al submenú para gestionar copias de seguridad"),
                        new Permiso(null, "IMPORTAR_BD", "Permite restaurar una copia de seguridad desde un archivo local"),
                        new Permiso(null, "EXPORTAR_BD", "Permite exportar una copia de seguridad de la base de datos")

                ));
                System.out.println("✅ Permisos precargados correctamente.");
            }

            //  6. Asignar permisos al rol ADMIN
            if (rolPermisoRepository.findByRol_IdRol(rolAdmin.getIdRol()).isEmpty()) {
                List<Permiso> permisos = permisoRepository.findAll();
                for (Permiso permiso : permisos) {
                    RolPermiso rp = new RolPermiso();
                    rp.setRol(rolAdmin);
                    rp.setPermiso(permiso);
                    rolPermisoRepository.save(rp);
                }
                System.out.println("✅ Permisos asignados al rol ADMIN.");
            }

        };
    }
}
