package pl.brickrental.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Min(18)
    private int age;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;


    public static User convert(UserDTO userDTO) {
        return new User(userDTO.id(), userDTO.firstName(), userDTO.lastName(), userDTO.age(), userDTO.email(), userDTO.password());
    }
}
