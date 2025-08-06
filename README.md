# Foro Hub Api

API RESTful desarrollada con **Spring Boot**, cuyo objetivo es manejar temas y comentarios hechos por usuarios en un foro.
Pensada para facilitar la creación y participación en conversaciones organizadas por temas.

## Características principales

- 🔐 Autenticación JWT: Registro y login de usuarios.
- 🗣️ Temas: Crear, listar, actualizar y eliminar temas.
- 💬 Comentarios: Agregar comentarios a temas.
- 👤 Usuarios: Asociados automáticamente mediante token.

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Security
- JWT
- JPA / Hibernate
- MySQL
- Gradle

## Endpoints

### Autenticación (`/auth`) 
- `POST /login`: Login con email y contraseña.
- `POST /signup`: Registro y login automático tras registro.

### Temas (`/topic`) 🔐

- `GET /`: Lista paginada de temas.
- `GET /{id}`: Obtener tema con comentarios.
- `POST /`: Crear nuevo tema (requiere token).
- `PUT /{id}`: Actualizar tema (requiere token).
- `DELETE /{id}`: Eliminar tema (requiere token).

### Comentarios (`/comment`) 🔐

- `POST /`: Crear un comentario en un tema (requiere token).
- *Mas por implementar...*

## Estructura del proyecto

```
📁 forohubapi
├── controller         # Controladores REST
├── dto               # Clases DTO para entrada y salida de datos
├── model             # Entidades del modelo
├── repository        # Repositorios JPA
├── service           # Lógica de negocio
├── config            # Configuración de seguridad y otros
└── exceptions        # Manejo de errores personalizados
```

## Ejecución

1. Clonar el repositorio:
```
git clone https://github.com/SamuelESuarezE/forohubapi
```
2. Configura el `application.yaml` siguiendo el template `application.example.yaml`.
3. Ejecuta con tu IDE. 🚀