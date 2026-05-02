# Franchise API

API REST para la gestión de franquicias, sucursales y productos, desarrollada con enfoque reactivo y desplegable en contenedores Docker.

---
## Descripción
Franchise API es una aplicación que permite gestionar franquicias, sus sucursales y productos asociados.
La solución ofrece las siguientes funcionalidades:

- Creación y actualización de franquicias
- Creación de sucursales asociadas a una franquicia
- Actualización del nombre de las sucursales
- Creación de productos con control de stock por sucursal
- Actualización del nombre y stock de los productos
- Consulta del producto con mayor stock por sucursal dentro de una franquicia

## Entorno de desarrollo

El proyecto fue desarrollado con versiones recientes de las siguientes tecnologías. Se recomienda utilizar versiones iguales o superiores.
* **Java JDK: 17 o superior**
* **spring-boot:4.0.6 (WebFlux)** – Programación reactiva
* **Spring Data MongoDB Reactive**
* **MongoDB 6**
* **Apache Maven (uso mediante Maven Wrapper mvnw)**
* **Docker & Docker Compose**
* **Lombok**
* **Jakarta Validation**

---
## Estructura del proyecto
El proyecto sigue una arquitectura basada en capas (inspirada en Clean Architecture):

```
franchise-api
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.franchise.api
│   │   │       ├── application
│   │   │       │   ├── usecase       → Casos de uso (lógica de negocio)
│   │   │       │   └── dto           → DTOs de salida
│   │   │       │
│   │   │       ├── domain
│   │   │       │   └── model         → Entidades del dominio
│   │   │       ├── config            → Configuraciones globales (CORS, etc.)
│   │   │       │
│   │   │       └── infrastructure
│   │   │           ├── controller    → Endpoints REST
│   │   │           ├── dto           → DTOs de entrada
│   │   │           └── persistence   → Repositorios MongoDB
│   │   │
│   │   └── resources
│   │       └── application.yaml     → Configuración de la aplicación (MongoDB, puertos, etc.)
│   │
│   └── test
│       └── java
│           └── com.franchise.api    → Pruebas unitarias
│
├── postman
│   └── Franchise-API.postman_collection.json → Colección de pruebas de la API
│
├── Dockerfile                 → Configuración para construir la imagen de la API
├── docker-compose.yml         → Orquestación de servicios (API + MongoDB)
│
├── pom.xml                    → Gestión de dependencias (Maven)
└── README.md                  → Documentación del proyecto
```
---

## Base de datos

Se utiliza **MongoDB** como base de datos NoSQL.

Configuración por defecto:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://mongo:27017/franchise_db
```
---

## Validaciones

Se implementan validaciones con Jakarta Validation:

* `@NotBlank` para campos de texto
* `@NotNull`, `@Min` para valores numéricos

Ejemplo:

```json
{
  "name": "Producto A"
}
```

```json
{
  "stock": 10
}
```

---

## Programación reactiva

El proyecto utiliza **Spring WebFlux**, trabajando con:

* `Mono<T>` → respuesta única
* `Flux<T>` → múltiples elementos

Esto permite:

* Mayor eficiencia en operaciones I/O
* Mejor escalabilidad

---
## Integración con el frontend

La arquitectura se basa en el consumo de una API RESTful por parte del cliente desarrollado en Ionic + Angular. Para asegurar la compatibilidad entre ambos sistemas, se aplicó un mapeo de dominio:

Para cumplir con los criterios de aceptación de la prueba y asegurar una integración fullstack completa, se implementó un flujo de datos bidireccional donde la entidad Categoría en el frontend actúa como el reflejo operativo de la entidad Franquicia en el backend.
1. Operaciones de Persistencia Sincronizada
- Lectura (Read): Al listar las categorías en la aplicación, el frontend realiza una petición GET a la API. El backend consulta la colección de franquicias en MongoDB y devuelve los registros que el frontend mapea y renderiza como categorías en la interfaz.
- Edición (Update): Cuando el usuario modifica el nombre o las propiedades de una categoría, se emite una petición PATCH hacia el backend. El UpdateFranchiseUseCase localiza el registro por su ID y actualiza la entidad franquicia, manteniendo la integridad de la información en ambas capas.
- Eliminación (Delete): Al borrar una categoría, el sistema ejecuta una petición DELETE. El backend valida primero las reglas de integridad referencial (verificando que no existan sucursales vinculadas) antes de remover permanentemente la franquicia de la base de datos.
2. Justificación Técnica del Modelo

   Esta conexión se diseñó bajo los siguientes principios:
- Cumplimiento de Reglas de Negocio: La arquitectura permite que el frontend mantenga una terminología amigable y orientada a la organización de tareas (Categorías), mientras que el backend cumple con los requisitos técnicos de gestionar una estructura de Franquicias requerida por la prueba.
- Integridad de la Conexión Fullstack: Al conectar estos dos conceptos, se valida la capacidad de la aplicación para transformar modelos de datos entre capas, manejar respuestas asíncronas y asegurar la consistencia de la información en tiempo real.

### Configuración de CORS y Acceso Externo
Para permitir la comunicación bidireccional entre el cliente (Ionic) y el servidor (Spring Boot), se ha implementado una política de CORS (Cross-Origin Resource Sharing).

Entorno de Desarrollo Local
Por defecto, la API está configurada para aceptar peticiones desde el entorno estándar de desarrollo de Ionic:
```
config.addAllowedOrigin("http://localhost:8100");
```
#### Pruebas en Dispositivos Móviles (Red Local)
Para realizar pruebas de integración en dispositivos físicos o emuladores que se encuentren en la misma red local (LAN), es necesario habilitar el acceso desde la IP del equipo host.

Identificar la IP local: En la terminal de su equipo, ejecute ipconfig (Windows) o ifconfig (Unix/Mac).

Configurar el origen: En franchise-api\src\main\java\com\franchise\api\config\CorsConfig.java, localice y configure la línea correspondiente:

// Reemplazar con la dirección obtenida, ej: 192.168.1.15
```
config.addAllowedOrigin("http://<TU_IP_LOCAL>:8100");
```

## Instalación del repositorio

Clonar el repositorio desde GitHub:
```bash
git clone https://github.com/tu-usuario/franchise-api.git
```

Ingresar al directorio del proyecto:
```bash
cd franchise-api
```
## Ejecución con Docker

Construir y levantar los servicios:

```bash
docker compose up --build
```

La API estará disponible en:

```
http://localhost:8081
```
---
## Ejecución de pruebas (Postman)

Endpoints

### Franquicias
- POST /franchises
- PATCH /franchises/{franchiseId}/name

### Sucursales
- POST /franchises/{franchiseId}/branches
- PATCH /franchises/branches/{branchId}/name

### Productos
- POST /branches/{branchId}/products
- DELETE /branches/products/{productId}
- PATCH /branches/products/{productId}/stock
- PATCH /branches/products/{productId}/name

### Consultas
- GET /franchises/{franchiseId}/top-products
---

Ejemplo:

```http
PATCH /franchises/{franchiseId}/top-products
```

```json
[
    {
        "branchName": "Sucursal Envigado",
        "productName": "Cuenta de ahorros",
        "stock": 150
    }
]
```
---
## Cómo probar la API

1. Importar la colección de Postman incluida
2. Ejecutar los endpoints disponibles
3. Validar respuestas según los casos de uso

Se incluye una colección de Postman con todos los endpoints del proyecto:
```
/postman/Franchise-API.postman_collection.json
```
---
## Pruebas unitarias

El proyecto incluye pruebas unitarias enfocadas en validar funcionalidades clave de la aplicación.

Se implementaron pruebas para:

- Creación de franquicias
- Consulta del producto con mayor stock por franquicia
  
Ejecución de pruebas

Para ejecutar todas las pruebas:
```
.\mvnw test
```
Para ejecutar una prueba específica:
```
.\mvnw -Dtest=NombreDeLaClaseTest test
```
Las pruebas se desarrollaron utilizando:

- JUnit 5 (Jupiter)
- Mockito
- Reactor Test (StepVerifier)

Estas pruebas permiten verificar el correcto funcionamiento de la lógica de negocio y asegurar la calidad del sistema.

## Decisiones técnicas

- Uso de **Spring WebFlux** para manejo reactivo
- Uso de **PATCH** para actualizaciones parciales
- Separación de responsabilidades mediante casos de uso (Clean Architecture)
- Uso de **MongoDB** por su flexibilidad para estructuras jerárquicas
- Contenerización con Docker para facilitar despliegue

---
