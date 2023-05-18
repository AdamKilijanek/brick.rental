package pl.brickrental.product;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Category category;
    @NotNull
    private String number;
    @NotNull
    private int elements;
    @NotNull
    private double price;

    public static Product convert(ProductDTO productDTO){
        return new Product(productDTO.id(), Category.convert(productDTO.categoryDTO()), productDTO.number(), productDTO.elements(), productDTO.price());
    }
}
