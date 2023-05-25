package pl.brickrental.product;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    public static Category convert(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.id(), categoryDTO.type());
    }
}
