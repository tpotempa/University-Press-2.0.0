package pl.edu.atar.university.press.data.service;

import org.springframework.stereotype.Service;
import pl.edu.atar.university.press.data.dto.PaperBookDto;
import pl.edu.atar.university.press.data.dto.AuthorDto;
import pl.edu.atar.university.press.data.model.PaperBook;
import pl.edu.atar.university.press.data.model.Author;
import pl.edu.atar.university.press.data.repository.PaperBookRepository;
import pl.edu.atar.university.press.data.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final PaperBookRepository paperBookRepository;
    private final AuthorRepository authorRepository;

    public BookService(PaperBookRepository paperBookRepository, AuthorRepository authorRepository) {
        this.paperBookRepository = paperBookRepository;
        this.authorRepository = authorRepository;
    }

    public PaperBook createPaperBookWithAuthors(PaperBookDto paperBookDto) {
        PaperBook paperBook = new PaperBook(
            paperBookDto.getIsbn(),
            paperBookDto.getTitle(),
            paperBookDto.getDescription(),
            paperBookDto.getPublishingDate(),
            paperBookDto.getBinding()
        );

        List<Author> authors = paperBookDto.getAuthors().stream()
            .map(this::findOrCreateAuthor)
            .collect(Collectors.toList());

        paperBook.setAuthors(authors);

        return paperBookRepository.save(paperBook);
    }

    private Author findOrCreateAuthor(AuthorDto authorDto) {
        return authorRepository.findByFirstNameAndLastName(authorDto.getFirstName(), authorDto.getLastName())
            .orElseGet(() -> {
                Author newAuthor = new Author(authorDto.getFirstName(), authorDto.getLastName());
                return authorRepository.save(newAuthor);
            });
    }
}