package dev.edgarpadilla.products.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.edgarpadilla.products.domain.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, String> {

    // Método personalizado para encontrar los 5 productos con precios menores a un valor dado
    List<Product> findTop5ByPriceLessThanEqualOrderByPriceDesc(double price);

}

