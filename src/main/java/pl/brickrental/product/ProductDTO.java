package pl.brickrental.product;

import pl.brickrental.category.Category;

import javax.persistence.OneToOne;

public record ProductDTO(Long id, @OneToOne Category category, String number, int elements, double price) {
}
