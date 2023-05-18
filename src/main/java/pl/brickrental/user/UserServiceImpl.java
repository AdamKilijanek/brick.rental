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
        return userRepository.findAll().stream().map(UserDTO::convert).toList();
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        User entity = userRepository.save(User.convert(user));
        return UserDTO.convert(entity);
    }

    @Override
    public UserDTO getById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::convert)
                .orElseThrow(() -> new RuntimeException("No user with id = " + id));
    }


    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }



}