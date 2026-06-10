package pl.edu.atar.university.press.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@NamedEntityGraph(name = "AudioBook.authors",
        attributeNodes = @NamedAttributeNode("authors")
)
@NamedEntityGraph(name = "AudioBook.authors-categories",
        attributeNodes = {
                @NamedAttributeNode("authors"),
                @NamedAttributeNode("categories")
        }
)

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class AudioBook extends Book {

    private String narrator;
    private int duration;

    @ManyToMany(fetch = EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Author> authors = new ArrayList<>();

    @ManyToMany(fetch = EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Category> categories = new LinkedHashSet<>();

    public AudioBook() {
    }

    public AudioBook(String isbn, String title, String description, LocalDate publishingDate, String narrator, int duration) {
        super(isbn, title, description, publishingDate);
        this.narrator = narrator;
        this.duration = duration;
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

}
