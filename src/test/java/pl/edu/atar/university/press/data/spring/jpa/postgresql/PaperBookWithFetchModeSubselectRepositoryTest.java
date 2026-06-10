package pl.edu.atar.university.press.data.spring.jpa.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.atar.university.press.data.model.PaperBookWithFetchModeSubselect;
import pl.edu.atar.university.press.data.repository.BookRepository;
import pl.edu.atar.university.press.data.repository.PaperBookWithFetchModeSubselectRepository;

class PaperBookWithFetchModeSubselectRepositoryTest
        extends BookRepositoryTest<PaperBookWithFetchModeSubselect> {

    @Autowired
    private PaperBookWithFetchModeSubselectRepository paperBookRepository;

    @Override
    PaperBookWithFetchModeSubselect createBook() {
        return new PaperBookWithFetchModeSubselect();
    }

    @Override
    BookRepository<PaperBookWithFetchModeSubselect> getBookRepository() {
        return paperBookRepository;
    }

}