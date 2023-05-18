package pl.brickrental.product;


public record CategoryDTO(Long id, CategoryType type) {

    public static CategoryDTO convert(Category category) {
        return new CategoryDTO(category.getId(), category.getType());
    }
}
