package pl.edu.atar.university.press.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.atar.university.press.data.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}