package dev.edgarpadilla.products.service;

import java.util.List;
import dev.edgarpadilla.products.dto.ProductDto;


public interface IProductsService {

    /* CRUD METHODS */

    /** Java Documentation
     * @param Product
     */
    void createProduct(ProductDto productDto);

    /**
     * @return All Products
     */
    public List<ProductDto> getAllProducts();

    /**
     * @param Product
     * @return boolean indicating if the update of a Product details is successful or not
     */
    boolean updateProduct(String codebar, ProductDto product);
    
    /**
     * @param codebar - Input codebar
     * @return boolean indicating if the delete of Product details is successful or not 
     */
    boolean deleteProduct(String codebar);


    /* Additional Methods */
    /**
     * @param codebar
     * @return Product Details based on a given codebar
     */
    ProductDto getProductByCodebar(String codebar);

      /**
     * @return Top 20 Products
     */
    public List<ProductDto> getTop20ProductsByPrice();

    /**
     * @return Top 5 Products filtered by price
     */
    public List<ProductDto> getTop5ProductsFilteredByPrice(double maxPrice);

    
}
