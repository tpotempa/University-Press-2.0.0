package pl.edu.atar.university.press.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.atar.university.press.data.model.Book;

import java.util.List;

@NoRepositoryBean
public interface BookRepository<T extends Book> extends JpaRepository<T, Long> {

    List<T> findByTitleContaining(String title, Pageable pageable);
}