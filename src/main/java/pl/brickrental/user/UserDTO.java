package pl.brickrental.user;

public record UserDTO(Long id, String firstName, String lastName, int age, String email, String password) {

    public static UserDTO convert(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getAge(), user.getEmail(), user.getPassword());
    }
}
