package pl.brickrental.product;

public record ProductDTO(Long id, CategoryDTO categoryDTO, String number, int elements, double price) {

    public static ProductDTO convert(Product product){
        return new ProductDTO(product.getId(), CategoryDTO.convert(product.getCategory()), product.getNumber(), product.getElements(), product.getPrice());
    }
}
