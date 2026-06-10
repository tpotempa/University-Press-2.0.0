package pl.edu.atar.university.press.data.spring.jpa.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.atar.university.press.data.model.PaperBookWithFetchModeSelect;
import pl.edu.atar.university.press.data.repository.BookRepository;
import pl.edu.atar.university.press.data.repository.PaperBookWithFetchModeSelectRepository;

class PaperBookWithFetchModeSelectRepositoryTest
        extends BookRepositoryTest<PaperBookWithFetchModeSelect> {

    @Autowired
    private PaperBookWithFetchModeSelectRepository paperBookRepository;

    @Override
    PaperBookWithFetchModeSelect createBook() {
        return new PaperBookWithFetchModeSelect();
    }

    @Override
    BookRepository<PaperBookWithFetchModeSelect> getBookRepository() {
        return paperBookRepository;
    }

}