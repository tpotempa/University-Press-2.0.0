package pl.edu.atar.university.press.data.model;

import jakarta.persistence.*;
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
@NamedEntityGraph(name = "PaperBook.authors",
        attributeNodes = @NamedAttributeNode("authors")
)
@NamedEntityGraph(name = "PaperBook.authors-categories",
        attributeNodes = {
                @NamedAttributeNode("authors"),
                @NamedAttributeNode("categories")
        }
)

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class PaperBook extends Book {

    private String binding;


    @ManyToMany(fetch = EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Fetch(FetchMode.SUBSELECT)
    private List<Author> authors = new ArrayList<>();

//@   ManyToMany(fetch = EAGER, cascade = {CascadeType.MERGE})
//    @Fetch(FetchMode.SUBSELECT)
    private Set<Category> categories = new LinkedHashSet<>();

    public PaperBook() {
    }

    public PaperBook(String isbn, String title, String description, LocalDate publishingDate, String binding) {
        super(isbn, title, description, publishingDate);
        this.binding = binding;
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