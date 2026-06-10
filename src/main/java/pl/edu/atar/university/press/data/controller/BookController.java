package pl.edu.atar.university.press.data.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.atar.university.press.data.model.Author;
import pl.edu.atar.university.press.data.model.PaperBook;
import pl.edu.atar.university.press.data.repository.AuthorRepository;
import pl.edu.atar.university.press.data.repository.PaperBookRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BookController {

    final PaperBookRepository paperBookRepository;
    final AuthorRepository authorRepository;

    public BookController(PaperBookRepository paperBookRepository, AuthorRepository authorRepository) {
        this.paperBookRepository = paperBookRepository;
        this.authorRepository = authorRepository;
    }

    private Author findOrCreateAuthor(Author author) {
        return authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName())
                .orElseGet(() -> authorRepository.save(author));
    }

    @GetMapping("/universitypress")
    public ResponseEntity<List<PaperBook>> getAllPaperBooks(@RequestParam(required = false) String title) {
        try {
            List<PaperBook> books = new ArrayList<>();

            if (title == null) paperBookRepository.findAll().forEach(books::add);
            else paperBookRepository.findByTitleContaining(title).forEach(books::add);

            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<PaperBook> getBookById(@PathVariable("id") long id) {
        Optional<PaperBook> bookData = paperBookRepository.findById(id);

        if (bookData.isPresent()) {
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<PaperBook> createPaperBookWithAuthors(@RequestBody PaperBook paperBook) {
        try {
            List<Author> authors = paperBook.getAuthors().stream()
                    .map(this::findOrCreateAuthor)
                    .collect(Collectors.toList());

            paperBook.setAuthors(authors);
            PaperBook savedBook = paperBookRepository.save(paperBook);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<PaperBook> updateBook(@PathVariable("id") long id, @RequestBody PaperBook paperBook) {
        Optional<PaperBook> bookData = paperBookRepository.findById(id);

        if (bookData.isPresent()) {
            PaperBook book = bookData.get();
            book.setTitle(paperBook.getTitle());
            book.setDescription(paperBook.getDescription());
            book.setPublishingDate(paperBook.getPublishingDate());
            return new ResponseEntity<>(paperBookRepository.save(book), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
        try {
            paperBookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/books")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        try {
            paperBookRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/books/published")
    public ResponseEntity<List<PaperBook>> findByPublished() {
        try {
            List<PaperBook> books = paperBookRepository.findByPublishingDateBefore(LocalDate.now(), Sort.by(ASC, "publishingDate"));

            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}