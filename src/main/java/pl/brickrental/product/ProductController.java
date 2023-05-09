package pl.brickrental.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/shop/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id){
        return productService.getById(id);
    }
    @PutMapping("/{id}")
    public ProductDTO updateProduct(@RequestBody ProductDTO product){
       return productService.updateProduct(product);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }
    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO product){
        return productService.createProduct(product);
    }
    @GetMapping("/")
    public List<ProductDTO> getAllProducts(){
        return productService.listAllProducts();
    }
}
