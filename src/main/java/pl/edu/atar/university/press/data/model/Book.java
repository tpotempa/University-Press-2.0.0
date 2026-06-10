package pl.edu.atar.university.press.data.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
@Data
public abstract class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String isbn;

    @Nonnull
    private String title;

    @Nullable
    private String description;

    private LocalDate publishingDate;

    protected Book() {
    }

    protected Book(String isbn, @Nonnull String title, @Nullable String description, LocalDate publishingDate) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.publishingDate = publishingDate;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nonnull String title) {
        this.title = title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public abstract List<Author> getAuthors();

    public abstract void setAuthors(List<Author> authors);

    public abstract Set<Category> getCategories();

    public abstract void setCategories(Set<Category> categories);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(title, book.title) &&
                Objects.equals(description, book.description) &&
                Objects.equals(publishingDate, book.publishingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, description, publishingDate);
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "Książka [ISBN={0}, Tytuł={1}, Opis={2}, Data wydania={3}]",
                isbn, title, description, publishingDate
        );
    }

}