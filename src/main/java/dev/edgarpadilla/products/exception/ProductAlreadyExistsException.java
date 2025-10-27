package dev.edgarpadilla.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductAlreadyExistsException extends RuntimeException {

     //constructor
    public ProductAlreadyExistsException(String message){
        super(message);
    }

}
