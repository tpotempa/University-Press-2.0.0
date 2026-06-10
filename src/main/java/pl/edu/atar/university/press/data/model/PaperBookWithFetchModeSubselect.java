package pl.edu.atar.university.press.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;
import static org.hibernate.annotations.FetchMode.SUBSELECT;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class PaperBookWithFetchModeSubselect extends Book {

    private String binding;

    @ManyToMany(fetch = EAGER)
    @Fetch(SUBSELECT)
    private List<Author> authors = new ArrayList<>();

    @ManyToMany
    @Fetch(SUBSELECT)
    private Set<Category> categories = new LinkedHashSet<>();

    public PaperBookWithFetchModeSubselect() {
    }

    public PaperBookWithFetchModeSubselect(String isbn, String title, String description, LocalDate publishingDate, String binding) {
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