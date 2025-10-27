package dev.edgarpadilla.products.mapper;

import dev.edgarpadilla.products.domain.Product;
import dev.edgarpadilla.products.dto.ProductDto;

// mapper entity dto on entity class in case some parameters do not want to be exposed through the http verbs 
// for this example audit parameters are not exposed
public class ProductMapper {

     public static ProductDto mapToProductDto(Product product, ProductDto productDto) {
        productDto.setCodebar(product.getCodebar());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public static Product mapToProduct(ProductDto productDto, Product product) {
        product.setCodebar(productDto.getCodebar());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice()); 
        return product;
    }

}
