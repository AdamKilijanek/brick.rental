package pl.brickrental.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> listAllProducts() {
        return productRepository.findAll().stream().map(this::convert).toList();
    }

    @Override
    public ProductDTO createProduct(ProductDTO product) {
        Product entity = productRepository.save(convert(product));
        return convert(entity);
    }

    @Override
    public ProductDTO getById(Long id) {
        return productRepository.findById(id).map(this::convert).orElseThrow(() -> new RuntimeException("No product with id =" + id));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        return productRepository.findById(productDTO.id())
                .map(product -> {
                    product.setCategory(productDTO.category());
                    product.setNumber(productDTO.number());
                    product.setElements(productDTO.elements());
                    product.setPrice(productDTO.price());
                    return product;
                })
                .map(productRepository::save)
                .map(this::convert)
                .orElseThrow(()-> new RuntimeException("No product with id = " + productDTO.id()));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convert(Product product){
        return new ProductDTO(product.getId(), product.getCategory(), product.getNumber(), product.getElements(), product.getPrice());
    }

    private Product convert(ProductDTO productDTO){
        return new Product(productDTO.id(), productDTO.category(), productDTO.number(), productDTO.elements(), productDTO.price());
    }
}
