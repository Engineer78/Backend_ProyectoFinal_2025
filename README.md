# Hardware Store Inventory API (Backend) 🖥️

Este proyecto representa el backend del sistema Hardware Store Inventory - FFIG, desarrollado en Java con Spring Boot. Forma parte de la Evidencia:GA8-220501096-AA1-EV01 del programa Tecnología en Análisis y Desarrollo de Software del SENA.

📋 Descripción

La API REST desarrollada permite gestionar de manera integral los datos del inventario de una ferretería, incluyendo productos, proveedores, categorías y sus relaciones, como tambien permite gestionar empleados del aferreteria incluyendo datos básicos, usuario y contraseña, perfiles, roles y tipo de documento.

Ofrece operaciones CRUD (Crear, Leer, Actualizar, Eliminar) completamente funcionales para los módulos de inventario y usuarios.

Este proyecto fue construido como un trabajo formativo colaborativo desde el SENA – Centro de Comercio y Turismo de Armenia, Regional Quindío.

📁 Estructura del Proyecto

Dentro del directorio src/main/resources/ se incluyen dos archivos esenciales:

Create Data Base.sql: contiene la estructura inicial de la base de datos.

DataEjemplo.sql: contiene datos de ejemplo para poblar la BD.

🧩 Se recomienda crear primero una base de datos vacía desde MySQL Workbench ejecutamdo el script de create Data Base.sql y luego ejecutar la API esta mapeará las entidades automáticamente usando JPA/Hibernate.
posteriormente se podra ejecutar el script DataEjemplo.sql cargando las tablas creads por la API con ejemplos.


⚙️ Configuración necesaria

Debes editar el archivo application.properties para configurar correctamente el acceso a tu base de datos:

spring.datasource.url=jdbc:mysql://localhost:3306/nombre_de_tu_bd

spring.datasource.username=TU_USUARIO

spring.datasource.password=TU_CONTRASEÑA

También puedes ajustar el puerto si deseas que la API escuche en uno distinto al 8080.

🚀 Tecnologías Utilizadas

Java

Spring Boot

Spring Data JPA

Maven

MySQL Server ✅

MySQL Connector/J ✅

MySQL Workbench ✅

Git / GitHub

🔀 Control de versiones y ramas

Se utiliza una rama intermedia llamada: feature/modulosUsuarios

Cada desarrollador trabajó desde su rama personal:

feature/juan

feature/ricardo

feature/joaquin

✅ Solo un integrante lideró el manejo de Git, centralizando los merges hacia la rama intermedia y evitando conflictos o sobreescrituras accidentales.

📦 Módulos entregados

Se entregan dos módulos funcionales con sus respectivos CRUD completos:

Módulo de Inventario

Módulo de Usuarios

🧪 Instalación y ejecución local

Prepara la base de datos:

Crea la base vacía desde MySQL Workbench.

Ejecuta los archivos Create Data Base.sql y DataEjemplo.sql en orden.

Edita el archivo application.properties con tus credenciales de conexión.

Ejecuta el backend desde la raíz del proyecto:

mvn spring-boot:run

La API quedará disponible en: http://localhost:8080

👥 Desarrolladores

Proyecto desarrollado por aprendices del SENA – Centro de Comercio y Turismo de Armenia, Regional Quindío:

Joaquín H. Jiménez Rosas (Ficha: 2879723)

Juan David Gallego López (Ficha: 2879723)

David Ricardo Graffe Rodríguez (Ficha: 2979724)

🎓 Instructora

Ing. Diana María Valencia Rebellón (SENA - Regional Quindío)

📝 Licencia

Este proyecto es de carácter formativo y no posee una licencia de distribución. Su uso está destinado exclusivamente a fines educativos dentro del SENA.

CopyRight © 2024-2025 @JuDaJoSystemSoft. All rights reserved.

