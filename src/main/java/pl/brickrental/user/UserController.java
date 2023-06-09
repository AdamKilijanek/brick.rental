package pl.brickrental.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/shop/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/")
    public List<UserDTO> getAllUsers() {
        return userService.listAllUsers();
    }

    @GetMapping("/email/{email}")
    public UserDTO findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/greaterThan/{age}")
    public List<UserDTO> findAllByAgeIsGreaterThan(@PathVariable int age) {
        return userService.findAllByAgeIsGreaterThan(age);
    }
}
