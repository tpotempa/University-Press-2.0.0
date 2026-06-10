package pl.edu.atar.university.press.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import pl.edu.atar.university.press.data.model.PaperBook;

import java.time.LocalDate;
import java.util.List;


public interface PaperBookRepository extends BookRepository<PaperBook> {

    List<PaperBook> findByTitleContaining(String title);

    List<PaperBook> findByPublishingDateBefore(LocalDate publishingDate, Sort sort);

    @EntityGraph("PaperBook.authors")
    List<PaperBook> findByPublishingDateAfter(LocalDate date, Sort sort);

    @EntityGraph("PaperBook.authors")
    List<PaperBook> findByPublishingDateAfter(LocalDate date, Pageable pageable);

    @EntityGraph("PaperBook.authors-categories")
    List<PaperBook> findByPublishingDateBetween(LocalDate startDate, LocalDate endDate, Sort sort);

    @Query("select b from PaperBook b join fetch b.authors" + " where b.publishingDate > :date" + " order by b.publishingDate asc")
    List<PaperBook> findByPublishingDateAfterJoinFetch(LocalDate date);

    @Query("select distinct b from PaperBook b join fetch b.authors" + " where b.publishingDate > :date" + " order by b.publishingDate asc")
    List<PaperBook> findByPublishingDateAfterJoinFetchDistinct(LocalDate date);

}