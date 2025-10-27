package dev.edgarpadilla.products.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dev.edgarpadilla.products.domain.Product;
import dev.edgarpadilla.products.dto.ProductDto;
import dev.edgarpadilla.products.exception.ProductAlreadyExistsException;
import dev.edgarpadilla.products.exception.ResourceNotFoundException;
import dev.edgarpadilla.products.mapper.ProductMapper;
import dev.edgarpadilla.products.repository.ProductsRepository;
import dev.edgarpadilla.products.service.IProductsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements IProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    /* CRUD Methods */

    // Create Product
    @Override
    public void createProduct(ProductDto productDto){

        Product product = ProductMapper.mapToProduct(productDto, new Product());
        Optional<Product> optionalProduct = productsRepository.findById(productDto.getCodebar());
        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product already stored with given codebar"
            + productDto.getCodebar());
        }

        productsRepository.save(product);

    }

    // Get all Products
    public List<ProductDto> getAllProducts(){

        List<Product> products = productsRepository.findAll();
        List<ProductDto> productsDto = new ArrayList<>();
        //ProductDto productDto = new ProductDto();

        for (Product product : products) {
            ProductDto productDto = ProductMapper.mapToProductDto(product, new ProductDto());
            productsDto.add(productDto);
        }

        return productsDto;
    }

    // // Update Product - AI generated code example
    // public Optional<Products> updateProduct(String codebar, Products updatedProduct) {
    //     return productRepository.findById(codebar).map(product -> {
    //         product.setDescription(updatedProduct.getDescription());
    //         product.setPrice(updatedProduct.getPrice());
    //         return productRepository.save(product);
    //     });
    // }

    /**
     * @param updatedProduct - Product Object to update
     * @return boolean indicating if the update of Product details is successful or not
     */
    @Override
    // Update Product - AI code replaced - Improved by adding Exception Handling and ResourceNotFoundException custom class
    public boolean updateProduct(String codebar, ProductDto updatedProduct){
        boolean isUpdated = false;

        Product product = productsRepository.findById(codebar).orElseThrow(
             () -> new ResourceNotFoundException("Product", "ProductCodebar", codebar)
        );
        
        // product.setDescription(updatedProduct.getDescription());
        // product.setPrice(updatedProduct.getPrice());

        ProductMapper.mapToProduct(updatedProduct, product);
        productsRepository.save(product);
        isUpdated=true;
        return isUpdated;
    }

    // Delete Product
     /**
     * @param codebar - Input Product codebar
     * @return boolean indicating if the delete of Product details is successful or not
     */
    @Override
    public boolean deleteProduct(String codebar){
        Product product = productsRepository.findById(codebar).orElseThrow(
             () -> new ResourceNotFoundException("Product", "ProductCodebar", codebar)
        );
        productsRepository.deleteById(product.getCodebar());
        return true;
    }


    /* Additional Methods */

     // Get Product (ProductDto without audit values) by id or return not found instead optional
    @Override
    public ProductDto getProductByCodebar(String codebar){
        Product product = productsRepository.findById(codebar).orElseThrow(
            ()-> new ResourceNotFoundException("Product", "codebar", codebar)
        );

        ProductDto productDto = ProductMapper.mapToProductDto(product, new ProductDto());
        return productDto;
    }

    // get limit 20
    public List<ProductDto> getTop20ProductsByPrice() {
        
        List<Product> products = productsRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(20)
                .collect(Collectors.toList());

        List<ProductDto> productsDto = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = ProductMapper.mapToProductDto(product, new ProductDto());
            productsDto.add(productDto);
        }

        return productsDto;
    }

    // get top 5 priceless 
    public List<ProductDto> getTop5ProductsFilteredByPrice(double maxPrice) {

        List<Product> products = productsRepository.findTop5ByPriceLessThanEqualOrderByPriceDesc(maxPrice);
        List<ProductDto> productsDto = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = ProductMapper.mapToProductDto(product, new ProductDto());
            productsDto.add(productDto);
        }

        return productsDto;
    }

}
