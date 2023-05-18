package pl.brickrental.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNumber(String number);

    Optional<Product> findByCategory(Category category);
}
