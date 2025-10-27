package dev.edgarpadilla.products.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    private String codebar;
    private String name;
    private String description;
    private double price;
    
}
