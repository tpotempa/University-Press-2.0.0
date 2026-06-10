package pl.edu.atar.university.press.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.atar.university.press.data.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}