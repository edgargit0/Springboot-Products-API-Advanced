package dev.edgarpadilla.products;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import dev.edgarpadilla.products.domain.Product;
import dev.edgarpadilla.products.repository.ProductsRepository;

@DataJpaTest
public class ProductsRepositoryTest {

    @Autowired
    private ProductsRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindById() {
        // 1. Preparar el entorno:
        // Se crea y persiste un producto de prueba en la base de datos H2 en memoria.
        // TestEntityManager es útil para interactuar directamente con la base de datos en los tests.
        Product product = new Product("0000054321", "Teclado Gaming", "teclado completo luces rgb español", 75.50);
        entityManager.persist(product);
        entityManager.flush(); // Sincroniza la base de datos con las operaciones pendientes.

        // 2. Ejecutar la prueba:
        // Se llama al método findById de nuestro repositorio.
        Optional<Product> foundProduct = productRepository.findById(product.getCodebar());

        // 3. Afirmar el resultado:
        // Se verifican las condiciones esperadas.
        assertThat(foundProduct).isPresent(); // Asegura que se encontró el producto.
        assertThat(foundProduct.get().getCodebar()).isEqualTo(product.getCodebar()); // Verifica que el código de barras coincida.
        assertThat(foundProduct.get().getName()).isEqualTo(product.getName()); // Verifica que el nombre del producto coincida.
        assertThat(foundProduct.get().getDescription()).isEqualTo(product.getDescription()); // Verifica la descripción.
        assertThat(foundProduct.get().getPrice()).isEqualTo(product.getPrice()); // Verifica el precio.
    }

    @Test
    void testFindById_WhenProductDoesNotExist() {
        // Preparar
        String nonExistentCodebar = "9999999999";

        // Ejecutar
        Optional<Product> foundProduct = productRepository.findById(nonExistentCodebar);

        // Afirmar
        assertThat(foundProduct).isNotPresent();
    }

}
