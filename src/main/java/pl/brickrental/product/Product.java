package pl.brickrental.product;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
    @NotBlank
    private String number;
    @NotNull
    private int elements;
    @NotNull
    private double price;

    public static Product convert(ProductDTO productDTO) {
        return new Product(productDTO.id(), Category.convert(productDTO.categoryDTO()), productDTO.number(), productDTO.elements(), productDTO.price());
    }

    public static List<Product> convert(List<ProductDTO> productDTO) {
        return productDTO.stream()
                .map(Product::convert)
                .collect(Collectors.toList());
    }
}
