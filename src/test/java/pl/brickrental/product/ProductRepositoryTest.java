package pl.brickrental.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository rut;

    @Test
    public void givenListProduct_whenSearchAllProductByIdIn_thenFindProduct() {
        Category category = new Category();
        category.setType(CategoryType.TECHNIC);
        entityManager.persist(category);
        List<Product> productList = List.of(
                Product.builder().category(category).number("42199").elements(1999).price(199.99).build(),
                Product.builder().category(category).number("42199").elements(1999).price(199.99).build());
        productList.forEach(entityManager::persist);

        List<Product> products = rut.findAllByIdIn(List.of(1L, 2L));

        assertEquals(productList, products);
    }
}