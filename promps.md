# 📋 Historial de Prompts para la Creación de la API RESTful de Productos con Google Gemini AI

Este archivo documenta la secuencia de solicitudes (prompts) utilizadas para generar el código de la API de Productos, desde la definición inicial hasta la adición de pruebas unitarias y documentación.

---

## 1. Definición Inicial de la API

| Solicitud Original | Resultado Esperado |
| :--- | :--- |
| **"Realiza en codigo Java-Spring boot una API Restful de obtetos 'Product' con los siguientes atributos: codebar (string), description (string), price"** | Código de una API monolítica con una sola clase, implementando CRUD para el objeto `Product`. |

---

## 2. Estructura y Persistencia (H2)

| Solicitud Original | Resultado Esperado |
| :--- | :--- |
| **"Separa el codigo por paquetes: model, repository, service, controler. Adicional integra BD H2"** | Refactorización del código separándolo en las capas `model`, `repository`, `service`, y `controller`, e integración de la base de datos en memoria H2 con Spring Data JPA. |

---

## 3. Consultas Avanzadas y Filtros

| Solicitud Original | Resultado Esperado |
| :--- | :--- |
| **"adiciona un endpoint que devuelva una lista de 20 productos ordenados por precio de mayor a menor"** | Nuevo método en el `Service` y `Controller` (`/products/top-by-price`) para devolver el Top 20 de productos por precio descendente. |
| **"adiciona otro endpoint que reciba en la uri el parametro description y que genere la suma de los precios de todas las entidades encontradas"** | Nuevo método en el `Repository` y `Service` para buscar por descripción y sumar los precios, expuesto en el `Controller` (`/products/sum-by-description`). |
| **"adiciona un endpoint que devuelva 5 entidades filtrando por el atributo precio"** | Nuevo método en el `Repository` y `Service` (`findTop5ByPriceLessThanEqualOrderByPriceDesc`) y endpoint (`/products/filter-by-price`) para filtrar y limitar resultados. |

---

## 4. Control de Respuesta HTTP

| Solicitud Original | Resultado Esperado |
| :--- | :--- |
| **"refactoriza el metodo getFilteredProducts del controlador, adicionando respuesta http 200 y si se da un valor parametro invalido, devuelve respuesta http 400"** | Refactorización del endpoint `/products/filter-by-price` para usar `ResponseEntity` y manejar explícitamente los estados `200 OK` y `400 BAD REQUEST` (por ejemplo, si el precio es negativo). |

---

## 5. DTO y Pruebas Unitarias

| Solicitud Original | Resultado Esperado |
| :--- | :--- |
| **"genera la clase productdto"** | Creación del `ProductDTO` (Data Transfer Object) con sus atributos, constructores y métodos *getters/setters*. |
| **"adiciona al proyecto el paquete test y realiza unit test de la interfaz ProductRepository, metodo getProductByCodebar, y decide si agregas la anotacion @DataJpaTest"** | Configuración del paquete `test`, y creación de `ProductRepositoryTest` utilizando `@DataJpaTest` para probar `findById` (el equivalente a `getProductByCodebar`). |
| **"adiciona a la clase ProductRepositoryTest la unit test para un producto por id que no existe"** | Adición de un caso de prueba negativo en `ProductRepositoryTest` para verificar que `findById` devuelva un `Optional.empty()` para IDs inexistentes. |
| **"adiciona al paquete test, pruebas mock de la clase ProductService con la interfaz ProductRepository para el metodo getAllProducts"** | Creación de `ProductServiceTest` usando `@SpringBootTest` y Mockito para simular el repositorio y probar el método `getAllProducts`. |
| **"refactoriza usando @ExtendWith(MockitoExtension.class)"** | Refactorización de `ProductServiceTest` reemplazando `@SpringBootTest` por `@ExtendWith(MockitoExtension.class)` para pruebas unitarias más rápidas. |
| **"genera 3 unit test para el repositorio"** | Adición de 3 pruebas más a `ProductRepositoryTest` para los métodos de consulta personalizados: `findTop5ByPrice...` y `findByDescriptionContainingIgnoreCase`. |
| **"resuelve el siguiente error: Failed to load ApplicationContext for... Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jpaAuditingHandler': Cannot create inner bean... while setting bean property 'auditorAware'"** | Solución al error común de `jpaAuditingHandler` sugiriendo la implementación de la interfaz `AuditorAware`. |
| **"genera un archivo llamado promps.md con los promps solicitados para la creación de toda la API"** | Solicitud actual que genera este documento. |