package pl.brickrental.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductService productService;

    private CategoryRepository categoryRepository;


    @BeforeEach
    public void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        categoryRepository = Mockito.mock(CategoryRepository.class);
        productService = new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Test
    public void givenNewProduct_whenAdd_thenReturnSavedProduct() {
        Category category = Category.builder().type(CategoryType.TECHNIC).build();
        Mockito.when(categoryRepository.findByType(CategoryType.TECHNIC)).thenReturn(Optional.of(category));

        ProductDTO productDTO = new ProductDTO(1L, new CategoryDTO(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99));

        ProductDTO actual = productService.createProduct(productDTO);

        assertEquals(new ProductDTO(1L, new CategoryDTO(1L, CategoryType.TECHNIC), "42199", 1999, 199.99), actual);
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void whenAddNewProduct_withNonExistingCategory_thenFail() {
        Mockito.when(categoryRepository.findByType(CategoryType.IDEAS)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.createProduct(new ProductDTO(1L, new CategoryDTO(1L, CategoryType.TECHNIC), "42199", 1999, 199.99)));

        assertThat(exception).hasMessage("No category with type TECHNIC");
    }


    @Test
    public void givenProduct_whenFindById_thenFindUser() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99)));

        ProductDTO actual = productService.getById(1L);
        assertEquals(1L, actual.id());
        assertEquals(ProductDTO.class, actual.getClass());
    }


    @Test
    public void givenProduct_whenUpdateProduct_thenReturnSavedProduct() {
        Category category = Category.builder().id(1L).type(CategoryType.TECHNIC).build();
        Mockito.when(categoryRepository.findByType(CategoryType.TECHNIC)).thenReturn(Optional.of(category));

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99)));

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 250.00));

        ProductDTO productDTO = new ProductDTO(1L, new CategoryDTO(1L, CategoryType.TECHNIC), "42199", 1999, 250.00);
        ProductDTO actual = productService.updateProduct(productDTO);


        assertEquals(new ProductDTO(1L, new CategoryDTO(1L, CategoryType.TECHNIC), "42199", 1999, 250.00), actual);
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void whenUpdateProduct_withNonExistingIdProduct_thenFail() {
        Category category = Category.builder().type(CategoryType.TECHNIC).build();
        Mockito.when(categoryRepository.findByType(CategoryType.TECHNIC)).thenReturn(Optional.of(category));
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.updateProduct(new ProductDTO(1L, new CategoryDTO(1L, CategoryType.TECHNIC), "42199", 1999, 199.99)));

        assertThat(exception).hasMessage("No product with id = 1");
    }

    @Test
    public void whenUpdateProduct_withNonExistingCategory_thenFail(){
        Mockito.when(categoryRepository.findByType(CategoryType.IDEAS)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.updateProduct(new ProductDTO(1L, new CategoryDTO(1L, CategoryType.TECHNIC), "42199", 1999, 199.99)));

        assertThat(exception).hasMessage("No category with type TECHNIC");
    }
}