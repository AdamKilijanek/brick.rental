package pl.brickrental.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> listAllUsers() {
        return userRepository.findAll().stream().map(this::convert).toList();
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        User entity = userRepository.save(convert(user));
        return convert(entity);
    }

    @Override
    public UserDTO getById(Long id) {
        return userRepository.findById(id)
                .map(this::convert)
                .orElseThrow(() -> new RuntimeException("No user with id = " + id));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return userRepository.findById(userDTO.id())
                .map(user -> {
                    user.setFirstName(userDTO.firstName());
                    user.setLastName(userDTO.lastName());
                    user.setAge(userDTO.age());
                    user.setEmail(userDTO.email());
                    user.setPassword(userDTO.password());
                    return user;
                })
                .map(userRepository::save)
                .map(this::convert)
                .orElseThrow(() -> new RuntimeException("No user with id = " + userDTO.id()));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convert(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getAge(), user.getEmail(), user.getPassword());
    }

    private User convert(UserDTO userDTO) {
        return new User(userDTO.id(), userDTO.firstName(), userDTO.lastName(), userDTO.age(), userDTO.email(), userDTO.password());
    }
}