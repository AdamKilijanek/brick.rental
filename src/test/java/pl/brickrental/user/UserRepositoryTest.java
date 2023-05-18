package pl.brickrental.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository rut;

    @Test
    public void givenUser_whenSearchForUserWithSameFirstName_thenFindUser(){
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        entityManager.persist(user);

        Optional<User> actual = rut.findByFirstName("John");

        assertThat(actual).isPresent();
        assertThat(actual).hasValue(user);
    }
    @Test
    public void givenUser_whenSearchForUserWithDifferentFirstName_thenFindNothing(){
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        entityManager.persist(user);

        Optional<User> actual = rut.findByFirstName("Tom");

        assertThat(actual).isEmpty();
    }
    @Test
    public void givenUser_whenSearchForUserWithSameLastName_thenFindUser() {
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        entityManager.persist(user);

        Optional<User> actual = rut.findByLastName("Doe");

        assertThat(actual).isPresent();
        assertThat(actual).hasValue(user);
    }
    @Test
    public void givenUser_whenSearchForUserWithDifferentLastName_thenFindNothing(){
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        entityManager.persist(user);

        Optional<User> actual = rut.findByFirstName("Dao");

        assertThat(actual).isEmpty();
    }

    @Test
    public void givenUser_whenSearchForUserWithSameEmail_thenFindUser(){
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        entityManager.persist(user);

        Optional<User> actual = rut.findByEmail("john.doe@gmail.com");

        assertThat(actual).isPresent();
        assertThat(actual).hasValue(user);
    }
    @Test
    public void givenUser_whenSearchForUserWitchDifferentEmail_thenFindNothing(){
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        entityManager.persist(user);

        Optional<User> actual = rut.findByEmail("jim.dao@hmail.com");

        assertThat(actual).isEmpty();
    }
}