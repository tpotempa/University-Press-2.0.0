package pl.edu.atar.university.press.data.spring.jpa.postgresql;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.atar.university.press.data.model.Author;
import pl.edu.atar.university.press.data.model.Book;
import pl.edu.atar.university.press.data.model.Category;
import pl.edu.atar.university.press.data.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.data.domain.Sort.Direction.DESC;

@ExtendWith(SpringExtension.class)
@TestInstance(PER_CLASS)
@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(false)
@Slf4j
abstract class BookRepositoryTest<T extends Book> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryTest.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AuthorRepository authorRepository;

    Category philosophy;
    Category history;
    Category psychology;

    Author tg;
    Author pd;
    Author ws;
    Author ms;
    Author jt;
    Author jw;
    Author js;
    Author ak;

    T khfw;
    T hfpg;
    T hfp;
    T m;
    @Autowired
    private PaperBookRepository paperBookRepository;
    @Autowired
    private PaperBookWithFetchModeJoinRepository paperBookWithFetchModeJoinRepository;
    @Autowired
    private PaperBookWithBatchSizeRepository paperBookWithBatchSizeRepository;
    @Autowired
    private PaperBookWithFetchModeSelectRepository paperBookWithFetchModeSelectRepository;
    @Autowired
    private PaperBookWithFetchModeSubselectRepository paperBookWithFetchModeSubselectRepository;

    abstract T createBook();

    abstract BookRepository<T> getBookRepository();

    @BeforeAll
    void setUp() {
        getBookRepository().deleteAll();
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
        paperBookRepository.deleteAll();
        paperBookWithBatchSizeRepository.deleteAll();
        paperBookWithFetchModeJoinRepository.deleteAll();
        paperBookWithFetchModeSelectRepository.deleteAll();
        paperBookWithFetchModeSubselectRepository.deleteAll();

        saveCategories();
        saveAuthors();
        saveBooks();
    }

    void saveCategories() {
        philosophy = new Category("Filozofia");
        history = new Category("Historia");
        psychology = new Category("Psychologia");

        categoryRepository.saveAll(List.of(history, philosophy, psychology));
    }

    void saveAuthors() {
        tg = new Author("Tadeusz", "Gadacz");
        pd = new Author("Paweł", "Dybel");
        ws = new Author("Wojciech", "Sady");
        ms = new Author("Magdalena", "Środa");
        jt = new Author("Józef", "Tischner");
        jw = new Author("Jan", "Woleński");
        js = new Author("Jan", "Skoczyński");
        ak = new Author("Antoni", "Kępiński");

        authorRepository.saveAll(List.of(tg, pd, ws, ms, jt, jw, js, ak));
    }

    void saveBooks() {
        khfw = createBook();
        khfw.setIsbn("978-02-016-3361-0");
        khfw.setTitle("Krótka historia filozofii współczesnej");
        khfw.setPublishingDate(LocalDate.parse("2023-10-06"));
        khfw.getAuthors().addAll(List.of(tg, pd, ws, ms));
        khfw.getCategories().add(philosophy);

        hfpg = createBook();
        hfpg.setIsbn("978-83-240-1022-6");
        hfpg.setTitle("Historia filozofii po górlasku");
        hfpg.setPublishingDate(LocalDate.parse("2011-06-22"));
        hfpg.getAuthors().add(jt);
        hfpg.getCategories().addAll(List.of(philosophy, history));

        hfp = createBook();
        hfp.setIsbn("978-83-750-5442-2");
        hfp.setTitle("Historia filozofii polskiej");
        hfp.setPublishingDate(LocalDate.parse("2010-05-01"));
        hfp.getAuthors().addAll(List.of(jw, js));
        hfp.getCategories().addAll(List.of(philosophy, history));

        m = createBook();
        m.setIsbn("860-29-005-7261-2");
        m.setTitle("Melancholia");
        m.setPublishingDate(LocalDate.parse("1974-01-01"));
        m.getAuthors().addAll(List.of(ak));
        m.getCategories().addAll(List.of(psychology));

        getBookRepository().saveAll(List.of(khfw, hfpg, hfp, m));
    }

    @AfterAll
    void cleanUp() {
        getBookRepository().deleteAll();
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void findById() {
        LOGGER.info("\nQuery with implicit method via interface CrudRepository with findById.");

        Optional<T> hfpg = getBookRepository().findById(this.hfpg.getId());
        LOGGER.info("The book was loaded.");
        assertThat(hfpg).hasValueSatisfying(equalTo(this.hfpg));
    }

    @Test
    void queryMethod() {
        LOGGER.info("\nQuery with explicit method.");

        List<T> books = getBookRepository().findByTitleContaining("Historia", PageRequest.of(0, 2, DESC, "publishingDate"));
        LOGGER.info("Books were loaded.");
        assertThat(books)
                .hasSize(2)
                .satisfies(equalTo(this.hfpg), atIndex(0))
                .satisfies(equalTo(this.hfp), atIndex(1));
    }

    Consumer<T> equalTo(T expected) {
        return actual -> {

            actual.getAuthors().forEach(a -> LOGGER.info(a + " / " + a.hashCode() + " / " + a.getClass()));
            expected.getAuthors().forEach(e -> LOGGER.info(e + " / " + e.hashCode() + " / " + e.getClass()));

            assertThat(actual.getId()).isEqualTo(expected.getId());
            assertThat(actual.getIsbn()).isEqualTo(expected.getIsbn());
            assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
            assertThat(actual.getPublishingDate()).isEqualTo(expected.getPublishingDate());
            assertThat(actual.getAuthors()).containsExactlyInAnyOrderElementsOf(expected.getAuthors());
            assertThat(actual.getCategories()).containsExactlyInAnyOrderElementsOf(expected.getCategories());
        };
    }

}