package pl.edu.atar.university.press.data.spring.jpa.postgresql;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.edu.atar.university.press.data.model.PaperBook;
import pl.edu.atar.university.press.data.repository.BookRepository;
import pl.edu.atar.university.press.data.repository.PaperBookRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
class PaperBookRepositoryTest extends BookRepositoryTest<PaperBook> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaperBookRepositoryTest.class);

    @Autowired
    private PaperBookRepository paperBookRepository;

    @Override
    PaperBook createBook() {
        return new PaperBook();
    }

    @Override
    BookRepository<PaperBook> getBookRepository() {
        return paperBookRepository;
    }

    @Test
    void entityGraph() {
        LOGGER.info("Query with @EntityGraph.");

        List<PaperBook> books = paperBookRepository
                .findByPublishingDateAfter(LocalDate.parse("2011-01-01"), Sort.by(ASC, "publishingDate"));

        LOGGER.info("Paper books were loaded for @entityGraph test.");
        assertThat(books)
                .hasSize(2)
                .satisfies(equalTo(this.hfpg), atIndex(0))
                .satisfies(equalTo(this.khfw), atIndex(1));
    }


    @Test
    void entityGraphWithPageable() {
        LOGGER.info("Query with @EntityGraph & Pageable.");

        List<PaperBook> books = paperBookRepository
                .findByPublishingDateAfter(
                        LocalDate.parse("2011-01-01"),
                        PageRequest.of(0, 1, ASC, "publishingDate")
                );
        LOGGER.info("Paper books were loaded for @entityGraphWithPageable test.");
        assertThat(books)
                .hasSize(1)
                .satisfies(equalTo(this.hfpg), atIndex(0));
    }

    @Test
    void entityGraphMultipleAttributeNodes() {
        LOGGER.info("Query with @EntityGraph with multiple attribute nodes.");

        List<PaperBook> books = paperBookRepository
                .findByPublishingDateBetween(
                        LocalDate.parse("2010-01-01"), LocalDate.parse("2014-12-31"),
                        Sort.by(ASC, "publishingDate")
                );
        LOGGER.info("Paper books were loaded for @entityGraphMultipleAttributeNodes test.");
        assertThat(books)
                .containsExactlyInAnyOrder(this.hfpg, this.hfp);

        assertThat(books.get(0).getAuthors())
                .hasSize(4)
                .containsExactlyInAnyOrder(jw, jw, js, js);
        assertThat(books.get(1).getAuthors())
                .hasSize(2)
                .containsExactlyInAnyOrder(jt, jt);

        // PYTANIE: Dlaczego (nie) pojawiają się duplikaty?
    }

    @Test
    void jpqlJoinFetch() {
        LOGGER.info("Query with @JPQL join fetch.");

        List<PaperBook> books =
                paperBookRepository.findByPublishingDateAfterJoinFetch(LocalDate.parse("2011-01-01"));
        LOGGER.info("Paper books were loaded for @jpqlJoinFetch test.");

        assertThat(books)
                .hasSize(2)
                .satisfies(equalTo(this.hfpg), atIndex(0))
                .satisfies(equalTo(this.khfw), atIndex(1));

        // PYTANIE: Dlaczego rezultat (nie) zawiera 5 pozycji?
    }

    @Test
    void jpqlJoinFetchDistinct() {
        LOGGER.info("Query with @JPQL join fetch & distinct.");

        List<PaperBook> books =
                paperBookRepository.findByPublishingDateAfterJoinFetchDistinct(LocalDate.parse("2011-01-01"));
        LOGGER.info("Books were loaded for @jpqlJoinFetchDistinct test");
        assertThat(books)
                .hasSize(2)
                .satisfies(equalTo(this.hfpg), atIndex(0))
                .satisfies(equalTo(this.khfw), atIndex(1));
    }

}