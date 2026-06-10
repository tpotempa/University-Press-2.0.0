package pl.edu.atar.university.press.data.spring.jpa.postgresql;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.atar.university.press.data.model.PaperBookWithFetchModeJoin;
import pl.edu.atar.university.press.data.repository.BookRepository;
import pl.edu.atar.university.press.data.repository.PaperBookWithFetchModeJoinRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PaperBookWithFetchModeJoinRepositoryTest extends BookRepositoryTest<PaperBookWithFetchModeJoin> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaperBookWithFetchModeJoinRepositoryTest.class);

    @Autowired
    private PaperBookWithFetchModeJoinRepository paperBookRepository;

    @Override
    PaperBookWithFetchModeJoin createBook() {
        return new PaperBookWithFetchModeJoin();
    }

    @Override
    BookRepository<PaperBookWithFetchModeJoin> getBookRepository() {
        return paperBookRepository;
    }

    @Test
    @Override
    void findById() {
        LOGGER.info("\nQuery with implicit method via interface CrudRepository with findById.");

        Optional<PaperBookWithFetchModeJoin> hfpg = paperBookRepository.findById(this.hfpg.getId());
        Optional<PaperBookWithFetchModeJoin> hfp = paperBookRepository.findById(this.hfp.getId());

        LOGGER.info("Books were loaded");

        assertThat(hfpg).hasValueSatisfying(book -> assertThat(book.getAuthors()).hasSize(1).containsExactlyInAnyOrder(jt));
        assertThat(hfp).hasValueSatisfying(book -> assertThat(book.getAuthors()).hasSize(2).containsExactlyInAnyOrder(jw, js));

        // PYTANIE: Czy (nie) pojawiają się duplikaty?
    }

}