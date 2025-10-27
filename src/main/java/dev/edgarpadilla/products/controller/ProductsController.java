package dev.edgarpadilla.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.edgarpadilla.products.constants.ProductsConstants;
import dev.edgarpadilla.products.dto.ProductDto;
import dev.edgarpadilla.products.dto.ResponseDto;
import dev.edgarpadilla.products.service.IProductsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "/products/v1", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
@Validated
public class ProductsController {

    @Autowired
    private IProductsService iProductService;

    /* CRUD METHODS */

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        iProductService.createProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ProductsConstants.STATUS_201, ProductsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productsDto = iProductService.getAllProducts();
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }


    @PutMapping("/update/{codebar}")
    public ResponseEntity<ResponseDto> updateProduct(@Valid @PathVariable String codebar, @RequestBody ProductDto updatedProduct){

        boolean isUpdated = iProductService.updateProduct(codebar, updatedProduct);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200, ProductsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ProductsConstants.STATUS_417, ProductsConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete/{codebar}")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable String codebar){

        boolean isDeleted = iProductService.deleteProduct(codebar);
         if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200, ProductsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ProductsConstants.STATUS_417, ProductsConstants.MESSAGE_417_DELETE));
        }
    }

    /* Additional Methods */

    @GetMapping("/fetch/{codebar}")
    public ResponseEntity<ProductDto> getProductByCodebar(@PathVariable 
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Codebar number must be 10 digits") 
                                                            String codebar) {
        ProductDto productDto = iProductService.getProductByCodebar(codebar);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(productDto);
    }

    @GetMapping("/fetch/top20")
    public ResponseEntity<List<ProductDto>> getTop20Products() {
        List<ProductDto> productsDto = iProductService.getTop20ProductsByPrice();
        // Retorna la lista de productos con un estado HTTP 200 (OK).
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    @GetMapping("/fetch/filter-by-max-price")
    public ResponseEntity<List<ProductDto>> getFilteredProductsByMaxPrice(@RequestParam double maxPrice) {
        // Valida que el valor del precio no sea negativo.
        if (maxPrice < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<ProductDto> productsDto = iProductService.getTop5ProductsFilteredByPrice(maxPrice);
        // Retorna la lista de productos con un estado HTTP 200 (OK).
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

}
