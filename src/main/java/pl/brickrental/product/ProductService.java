package pl.brickrental.product;

import java.util.List;

public interface ProductService {

    List<ProductDTO> listAllProducts();

    ProductDTO createProduct(ProductDTO product);

    ProductDTO getById(Long id);

    ProductDTO updateProduct(ProductDTO productDTO);

    void deleteById(Long id);

    List<ProductDTO> findAllByIdIn(List<Long> ids);
}
