# 💈 Barbería "El Mapache Bigotón" API & Frontend

Este repositorio contiene el proyecto completo para el sistema de gestión de citas de la barbería "El Mapache Bigotón".

Incluye:
* `barberia-api/`: Un backend API REST construido con **Spring Boot** para gestionar toda la lógica de negocio.
* `barberia-frontend/`: Un frontend de cliente construido con **HTML, CSS y JavaScript vainilla** para interactuar con la API.

---

## Backend (`barberia-api`)

API REST para la gestión de citas de la barbería, incluyendo registro de barberos, clientes, servicios y citas.

### Requisitos
* Java 17 (o superior).
* Maven 3.x
* Una base de datos MySQL en `localhost:3306` con una base de datos llamada `mapachedb`.

### Configuración
Este proyecto utiliza **variables de entorno** para manejar información sensible como las credenciales de la base de datos (ver Paso 1).

Antes de ejecutar, debes configurar las siguientes variables de entorno en tu sistema o en tu IDE:

```bash
# Usuario de tu base de datos MySQL
DB_USERNAME=tu_usuario_mysql

# Contraseña de tu base de datos MySQL
DB_PASSWORD=tu_contraseña_secreta