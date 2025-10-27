# 💻 Product RESTful API (Spring Boot & H2)

Esta es una API RESTful desarrollada con **Spring Boot** para la gestión de productos. Utiliza **Spring Data JPA** para la persistencia y la base de datos en memoria **H2** para el entorno de desarrollo y pruebas.

La API permite realizar operaciones CRUD básicas, así como consultas avanzadas como: obtener los 20 productos más caros, aplicar filtros por precio maximo y obtener los 5 productos debajo de dicho precio y obtener producto por id (codebar en este caso), que a su vez ejemplifica obtencion por nombre (string).

Se construyo con principalmente con conocimiento del Desarrollador en conjunto con apoyo de herramienta de inteligencia artificial Google Gemini AI

---

## 🏗️ Estructura del Proyecto

El proyecto sigue una arquitectura de capas bien definida:

| Paquete | Descripción |
| :--- | :--- |
| `controller` | Maneja las peticiones HTTP y define los *endpoints*. |
| `service` | Contiene la lógica de negocio y las operaciones complejas. |
| `repository` | Interfaz que interactúa con la base de datos (H2/JPA). |
| `model` | Entidad JPA (`Product`) que mapea la tabla de la base de datos. |
| `dto` | Objeto de Transferencia de Datos (`ProductDTO`). |
| `mapper` | Mapeo de la entidad Producto con el objeto DTO y sólo los atributos necesarios, evitando exponer atributos sensibles y/o no necesarios. |
| `test` | Contiene todas las pruebas unitarias (`@DataJpaTest`, `@ExtendWith`). |
| `audit` | Contiene la clase que implementa AuditorAware<T> para auditoria de creacion y modificacion de registros. |
| `exception` | Contiene las clases para el manejo de excepciones . |
| `constants` | Contiene una clase para manejar todos aquellos valores constantes como buena practica. |

---

## 🚀 Cómo Ejecutar la Aplicación

1.  **Requisitos:** Tener instalado Java (JDK 21+) y Maven (para ejecucion con comando mvn springboot).
2.  **Ejecutar:**
    ```bash
    mvn spring-boot:run or java -jar target/products-0.0.1-SNAPSHOT.jar
    ```
3.  **Acceso a la Consola H2:** Una vez iniciada, puedes acceder a la consola de la base de datos en: `http://localhost:8080/h2-console`
    * **JDBC URL:** `jdbc:h2:mem:productdb`
    * **User:** `sa`
    * **Password:** ` `

---

## 🧭 Endpoints de la API

La base de la URL para todos los endpoints es `http://localhost:8080/products/v1/`.

### Operaciones CRUD

| Método | Endpoint | Descripción | Cuerpo (Body) | Respuestas HTTP |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/fetch` | Obtiene la lista completa de productos. | *Ninguno* | `200 OK` |
| **GET** | `/fetch/{codebar}` | Obtiene un producto específico por su código de barras. | *Ninguno* | `200 OK`, `404 NOT FOUND` |
| **POST** | `/create` | Crea un nuevo producto. | Objeto `Product` (JSON) | `200 OK` (Devuelve el objeto creado) |
| **PUT** | `/update/{codebar}` | Actualiza un producto existente. | Objeto `Product` (JSON) | `200 OK`, `404 NOT FOUND` |
| **DELETE** | `/delete/{codebar}` | Elimina un producto por su código de barras. | *Ninguno* | `204 NO CONTENT`, `404 NOT FOUND` |

---

### Consultas Avanzadas

| Método | Endpoint | Descripción | Parámetros de Consulta | Respuestas HTTP |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/products/top20` | Devuelve los **20 productos** con los precios más altos, ordenados de mayor a menor. | *Ninguno* | `200 OK` |
| **GET** | `/products/filter-by-max-price` | Devuelve un máximo de **5 productos** cuyo precio sea menor o igual al valor proporcionado, ordenados de mayor a menor precio. | `?maxPrice={double}` (e.g., `?maxPrice=150.00`) | `200 OK`, `400 BAD REQUEST` (si `maxPrice` es negativo) |
| **GET** | `/products/sum-by-description` | Calcula la **suma total de precios** de todos los productos que contienen la cadena de texto proporcionada en su descripción. | `?description={string}` (e.g., `?description=Laptop`) | `200 OK` (Devuelve el total), `400 BAD REQUEST` (si la descripción está vacía), `404 NOT FOUND` (si la suma es 0) |

---

### Ejemplos de Peticiones para realizar testing

## Crear Producto
POST http://localhost:8080/products/v1/create
{
    "codebar": "0000054326",
    "name": "samsung6",
    "description": "galaxy z flip 6",
    "price": 16000.000000
}

Response:

-OK
http 201
{
    "statusCode": "201",
    "statusMsg": "Product created successfully"
}

-Duplicado codebar: 

http 400
{
    "apiPath": "uri=/products/v1/create",
    "errorCode": "BAD_REQUEST",
    "errorMessage": "Product already stored with given codebar0000054326",
    "errorTime": "2025-09-30T20:28:29.334926493"
}

-Codebar tamaño diferente a 10 digitos: "codebar": "000005432611111"

http 400
{
    "codebar": "The length of the product codebar should be between 5 and 10"
}

-Algún parametro faltante, ejemplo "description":

http 400
{
    "description": "Description can not be a null or empty"
}

## Obtener Producto por precio
Get http://localhost:8080/products/v1/fetch/0000054325

Response: 

Http 200 OK
{
    "codebar": "0000054326",
    "name": "samsung6",
    "description": "galaxy z flip 6",
    "price": 16000.0
}

Http 404 Not Found
{
    "apiPath": "uri=/products/v1/fetch/9999999999",
    "errorCode": "NOT_FOUND",
    "errorMessage": "Product not found with the given input data codebar: '9999999999'",
    "errorTime": "2025-09-30T20:38:20.563137551"
}

## Obtener Producto por precio maximo
http://localhost:8080/products/v1/fetch/filter-by-max-price?maxPrice=50000

Http 200 OK
[
    {
        "codebar": "0000054322",
        "name": "samsung2",
        "description": "galaxy z flip 2",
        "price": 12000.0
    },
    {
        "codebar": "0000054323",
        "name": "samsung3",
        "description": "galaxy z flip 3",
        "price": 13000.0
    },
    {
        "codebar": "0000054324",
        "name": "samsung4",
        "description": "galaxy z flip 4",
        "price": 14000.0
    },
    {
        "codebar": "0000054325",
        "name": "samsung5",
        "description": "galaxy z flip 5",
        "price": 25000.0
    },
    {
        "codebar": "0000054326",
        "name": "samsung6",
        "description": "galaxy z flip 6",
        "price": 36000.0
    }
]


## 📝 Esquema del Objeto `Product`

El modelo de datos principal utilizado en el cuerpo de las peticiones (POST, PUT) y las respuestas es:

```json
{
  "codebar": "string (clave primaria)",
  "name": "string",
  "description": "string",
  "price": "double"
}

---



