package dev.edgarpadilla.products;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.edgarpadilla.products.domain.Product;
import dev.edgarpadilla.products.dto.ProductDto;
import dev.edgarpadilla.products.repository.ProductsRepository;
import dev.edgarpadilla.products.service.impl.ProductsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

    @Mock
    private ProductsRepository productRepository;

    @InjectMocks
    private ProductsServiceImpl productsServiceImpl;

    private List<Product> mockProducts;

    @BeforeEach
    void setUp() {
        mockProducts = Arrays.asList(
            new Product("750100000001", "Laptop HP", "memoria ram 16Gb, SSD 250Gb, Display 14",850.50),
            new Product("750200000002", "Mouse Logitech", "luces rgb ergonomico", 99.99)
        );
    }

    @Test
    void testGetAllProducts() {
        // Configurar el mock:
        when(productRepository.findAll()).thenReturn(mockProducts);

        // Ejecutar el método del servicio:
        List<ProductDto> result = productsServiceImpl.getAllProducts();

        // Afirmar el resultado:
        assertEquals(2, result.size());
        assertEquals("Laptop HP", result.get(0).getName());
        assertEquals("Mouse Logitech", result.get(1).getName());
    }

}
