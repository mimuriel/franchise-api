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
