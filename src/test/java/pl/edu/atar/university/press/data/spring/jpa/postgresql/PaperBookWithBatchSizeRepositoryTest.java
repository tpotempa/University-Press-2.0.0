package pl.edu.atar.university.press.data.spring.jpa.postgresql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.atar.university.press.data.model.PaperBookWithBatchSize;
import pl.edu.atar.university.press.data.repository.BookRepository;
import pl.edu.atar.university.press.data.repository.PaperBookWithBatchSizeRepository;

@Slf4j
class PaperBookWithBatchSizeRepositoryTest extends BookRepositoryTest<PaperBookWithBatchSize> {

    @Autowired
    private PaperBookWithBatchSizeRepository paperBookRepository;

    @Override
    PaperBookWithBatchSize createBook() {
        return new PaperBookWithBatchSize();
    }

    @Override
    BookRepository<PaperBookWithBatchSize> getBookRepository() {
        return paperBookRepository;
    }

}