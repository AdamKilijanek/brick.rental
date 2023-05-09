package pl.brickrental.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByFirstName(String firstName);

    Optional<User> findByLastName(String lastName);
}
