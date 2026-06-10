package pl.edu.atar.university.press.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class PaperBookWithBatchSize extends Book {

    private String binding;

    @ManyToMany(fetch = EAGER)
    @BatchSize(size = 2)
    private List<Author> authors = new ArrayList<>();

    @ManyToMany
    @BatchSize(size = 2)
    private Set<Category> categories = new LinkedHashSet<>();

    public PaperBookWithBatchSize() {
    }

    public PaperBookWithBatchSize(String isbn, String title, String description, LocalDate publishingDate, String binding) {
        super(isbn, title, description, publishingDate);
        this.binding = binding;
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public void setAuthors(List<Author> authors) {

    }

    @Override
    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public void setCategories(Set<Category> categories) {

    }

}