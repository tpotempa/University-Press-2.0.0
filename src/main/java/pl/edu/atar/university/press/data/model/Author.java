package pl.edu.atar.university.press.data.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@Table(name = "author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String middleName;

    @Column(length = 50, nullable = false)
    private String lastName;

    private LocalDate dateOfBirth;

    @NaturalId
    @Column(length = 19, nullable = true, unique = true)
    private String orcid;

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author(String firstName, String middleName, String lastName, LocalDate dateOfBirth, String orcid) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.orcid = orcid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(firstName, author.firstName) &&
                Objects.equals(middleName, author.middleName) &&
                Objects.equals(lastName, author.lastName) &&
                Objects.equals(dateOfBirth, author.dateOfBirth) &&
                Objects.equals(orcid, author.orcid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, dateOfBirth, orcid);
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "Autor [ID={0}, Imię={1}, Drugie imię={2}, Nazwisko={3}, Data urodzenia={4}, ORCID={5}]",
                id, firstName, middleName, lastName, dateOfBirth, orcid
        );
    }

}