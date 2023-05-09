package pl.brickrental.user;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenUser_whenGetIdOfThat_thenReturnThatUser() throws Exception{
        UserDTO user = new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1");

        Mockito.when(userService.getById(1L)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/users/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Matchers.is(18)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("john.doe@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("John1")));
    }
    @Test
    public void givenUser_whenCreate_thenSaveThatUser() throws Exception{
        UserDTO user = new UserDTO(null, "John", "Doe", 18, "john.doe@gmail.com", "John1");
        Mockito.when(userService.createUser(user)).thenReturn(new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1"));

        mockMvc.perform(MockMvcRequestBuilders.post("/shop/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                        "id": null,
                        "firstName":"John",
                        "lastName":"Doe",
                        "age":18,
                        "email":"john.doe@gmail.com",
                        "password": "John1"
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Matchers.is(18)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("john.doe@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("John1")));
    }

    @Test
    public void givenUser_whenDeleteByIdThatUser_thenDeleteThatUser()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/shop/users/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void givenUsers_whenFindByAll_thenReturnAllUsers()throws Exception{
        List<UserDTO> userList = List.of(
                new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1"),
                new UserDTO(2L, "John2", "Doe2", 19, "john.doe2@gmail.com", "John2"));
        Mockito.when(userService.listAllUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/users/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age", Matchers.is(18)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("john.doe@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password", Matchers.is("John1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("John2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("Doe2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age", Matchers.is(19)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is("john.doe2@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].password", Matchers.is("John2")));
    }
}