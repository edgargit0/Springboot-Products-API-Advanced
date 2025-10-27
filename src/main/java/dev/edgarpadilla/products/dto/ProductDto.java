package dev.edgarpadilla.products.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProductDto {

    @NotEmpty(message = "Codebar can not be a null or empty")
    @Size(min = 5, max = 10, message = "The length of the product codebar should be between 5 and 10")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private String codebar;

    @NotEmpty(message = "Name can not be a null or empty")
    private String name;

    @NotEmpty(message = "Description can not be a null or empty")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @DecimalMax(value = "99999.99", message = "Price cannot exceed 9999.99")
    @Digits(integer = 5, fraction = 2, message = "Price must have at most 5 integer digits and 2 fractional digits")
    private double price;

}
