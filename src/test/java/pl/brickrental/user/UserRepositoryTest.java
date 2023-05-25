package pl.brickrental.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository rut;


    @Test
    public void givenUser_whenSearchForUserWithSameEmail_thenFindUser() {
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        entityManager.persist(user);

        Optional<User> actual = rut.findByEmail("john.doe@gmail.com");

        assertThat(actual).isPresent();
        assertThat(actual).hasValue(user);
    }

    @Test
    public void givenUser_whenSearchForUserWitchDifferentEmail_thenFindNothing() {
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        entityManager.persist(user);

        Optional<User> actual = rut.findByEmail("jim.dao@hmail.com");

        assertThat(actual).isEmpty();
    }

    @Test
    public void givenUsers_whenSearchForUserGreaterThan20_thenFindUser() {
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        User user1 = User.builder().firstName("John2").lastName("Doe2").age(25).email("john2.doe@gmail.com").password("John2").build();
        entityManager.persist(user);
        entityManager.persist(user1);

        List<User> actual = rut.findAllByAgeIsGreaterThan(20);

        assertThat(actual).size().isEqualTo(1);
    }

    @Test
    public void givenUsers_whenSearchForUserGreaterThan20_thenFindNothing() {
        User user = User.builder().firstName("John").lastName("Doe").age(18).email("john.doe@gmail.com").password("John1").build();
        User user1 = User.builder().firstName("John2").lastName("Doe2").age(25).email("john2.doe@gmail.com").password("John2").build();
        entityManager.persist(user);
        entityManager.persist(user1);

        List<User> actual = rut.findAllByAgeIsGreaterThan(26);

        assertThat(actual).isEmpty();
    }
}