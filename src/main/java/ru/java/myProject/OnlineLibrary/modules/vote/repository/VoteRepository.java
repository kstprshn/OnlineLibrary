package ru.java.myProject.OnlineLibrary.modules.vote.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;
import ru.java.myProject.OnlineLibrary.modules.vote.entity.Vote;


@Repository
@RepositoryRestResource
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @RestResource(path = "get-book-average-rating")
    @Query("select AVG(v.value) FROM Vote v WHERE v.book.id = :bookId")
    Double getAverageRatingByBookId(@Param("bookId") Long bookId);

    @RestResource(path = "count-total-votes")
    Integer countByBookId(Long bookId);

    boolean existsByUserAndBook(User user, Book book);
}