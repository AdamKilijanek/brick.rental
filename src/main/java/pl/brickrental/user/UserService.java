package pl.brickrental.user;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();

    UserDTO createUser(UserDTO user);

    UserDTO getById(Long id);

    UserDTO updateUser(UserDTO userDTO);

    void deleteById(Long id);
}
