package pl.brickrental.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceImplTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void givenUsers_whenFindByAll_thenFindAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(
                new User(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1"),
                new User(2L, "John2", "Doe2", 19, "john.doe2@gmail.com", "John2")));

        List<UserDTO> actual = userService.listAllUsers();

        assertThat(actual).containsExactly(
                new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1"),
                new UserDTO(2L, "John2", "Doe2", 19, "john.doe2@gmail.com", "John2"));
    }

    @Test
    public void givenNewUser_whenAdd_thenReturnSavedUser() {
        UserDTO userDTO = new UserDTO(null, "firstName", "lastName", 18, "email", "password");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1"));

        UserDTO actual = userService.createUser(userDTO);

        assertEquals(new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1"), actual);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void givenUser_whenFindById_thenFindUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1")));

        UserDTO actual = userService.getById(1L);
        assertEquals(1L, actual.id());
        assertEquals(UserDTO.class, actual.getClass());
    }
}