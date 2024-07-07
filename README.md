# VehicleCRUDSpring
Este repositorio contiene una aplicación desarrollada con Spring Boot y Thymeleaf, siguiendo el patrón de arquitectura MVC. La aplicación gestiona un CRUD completo para vehículos, permitiendo operaciones de creación, lectura, actualización y eliminación.

## Requisitos Previos

Antes de instalar y ejecutar la aplicación, asegúrate de tener instalados los siguientes componentes:

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [MySQL](https://dev.mysql.com/downloads/installer/)

- ## Instalación

Sigue estos pasos para instalar y configurar la aplicación:

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/VehicleCRUDSpring
```
### 2. Configurar la Base de datos

Asegúrate de tener MySQL instalado y ejecuta el script [ScriptDatabaseCreaction](https://github.com/angedramos/VehicleCRUDSpring/blob/main/ScriptVehiclesDatabase.sql) proporcionado para configurar la base de datos "vehicles".

### 3. Configurar el archivo "application.properties"

Una vez dentro del proyecto, edita el archivo `src/main/resources/application.properties` para que coincida con la configuración de tu base de datos (Deberás proveer tu usuario y contraseña de usuario de MySQL):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vehicles
spring.datasource.username=tu-usuario
spring.datasource.password=tu-contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false
```
### 4. Construir la aplicación

Una vez configurada la base de datos en EL proyecto, hay que ir al directorio raíz del proyecto y ejecutar el siguiente comando para construir la aplicación:
```bash
./mvnw clean install
```
### 5. Ejecutar la aplicación

Una vez construida la aplicación, ejecuta el siguiente comando para iniciarla:
```bash
./mvnw spring-boot:run
```
La aplicación estará disponible en http://localhost:8081.

- ## Uso
Una vez que la aplicación esté en funcionamiento, puedes acceder a las siguientes funcionalidades:

### CRUD de Vehículos

- Página de Inicio: http://localhost:8081/
- Crear Vehículo: http://localhost:8081/vehicles/addVehicle
- Listar Vehículos: http://localhost:8081/vehicles
- Buscar Vehículo: http://localhost:8081/vehicles/searchVehicle
- Editar Vehículo: http://localhost:8081/vehicles/edit/{id}
- Eliminar Vehículo: http://localhost:8081/vehicles/delete/{id}

