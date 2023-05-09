package pl.brickrental.user;

public record UserDTO(Long id, String firstName, String lastName, int age, String email, String password) {
}
