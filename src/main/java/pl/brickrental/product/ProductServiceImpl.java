package pl.brickrental.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductDTO> listAllProducts() {
        return productRepository.findAll().stream().map(ProductDTO::convert).toList();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findByType(productDTO.categoryDTO().type())
                .orElseThrow(
                        () -> new IllegalArgumentException("No category with type " + productDTO.categoryDTO().type()));
        Product product = Product.convert(productDTO);
        product.setCategory(category);
        Product entity = productRepository.save(product);
        return ProductDTO.convert(entity);
    }

    @Override
    public ProductDTO getById(Long id) {
        return productRepository.findById(id).map(ProductDTO::convert).orElseThrow(() -> new RuntimeException("No product with id =" + id));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findByType(productDTO.categoryDTO().type())
                .orElseThrow(
                        () -> new IllegalArgumentException("No category with type " + productDTO.categoryDTO().type()));
        return productRepository.findById(productDTO.id())
                .map(product -> {
                    product.setCategory(category);
                    product.setNumber(productDTO.number());
                    product.setElements(productDTO.elements());
                    product.setPrice(productDTO.price());
                    return product;
                })
                .map(productRepository::save)
                .map(ProductDTO::convert)
                .orElseThrow(() -> new RuntimeException("No product with id = " + productDTO.id()));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
