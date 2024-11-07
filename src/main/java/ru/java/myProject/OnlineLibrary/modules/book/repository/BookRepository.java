package ru.java.myProject.OnlineLibrary.modules.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Page<Book>> findByAuthorFioContainingIgnoreCase(String fio, Pageable pageable);

    Optional<List<Book>> findByNameContainingIgnoreCaseOrderByName(String name);


    /* При получении списка книг контент для каждой книги пустой.
     Только когда пользователь нажимает на чтение книги - делаем запрос в БД на получение контента
     при необходимости для избежания перегрузки системы
     */
    @Query("select new ru.java.myProject.OnlineLibrary.modules.book.entity.Book(b.id, b.name, b.page_count, b.isbn, b.genre, b.author, b.publisher, b.publish_year, b.image, b.descr, b.view_count, b.total_rating, b.total_vote_count, b.avg_rating) from Book b where b.genre.name=:genreName")
    Page<Book> findAllWithoutContent(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Book b set b.content=:content where b.id =:id")
    void updateContent(@Param("content") byte[] content, @Param("id") long id);

    @Query("select new ru.java.myProject.OnlineLibrary.modules.book.entity.Book(b.id, b.name, b.page_count, b.isbn, b.genre, b.author, b.publisher, b.publish_year, b.image, b.descr, b.view_count, b.total_rating, b.total_vote_count, b.avg_rating) from Book b where b.genre.name=:genreName")
    List<Book> findTopBooks(Pageable pageable);

    @Query("select new ru.java.myProject.OnlineLibrary.modules.book.entity.Book(b.id, b.name, b.page_count, b.isbn, b.genre, b.author, b.publisher, b.publish_year, b.image, b.descr, b.view_count, b.total_rating, b.total_vote_count, b.avg_rating) from Book b where b.genre.name=:genreName")
    Optional<Page<Book>> findByGenreName(@Param("genreName") String genre, Pageable pageable);

    @Query("select b.content FROM Book b where b.id = :id")
    Optional<byte[]> getContent(@Param("id") long id);

    @Modifying
    @Query("update Book b set b.view_count=:viewCount where b.id =:id")
    void updateViewCount(@Param("viewCount") long viewCount, @Param("id") long id);

    @Modifying
    @Query("update Book b set b.total_vote_count=:totalVoteCount, b.total_rating=:totalRating, b.avg_rating=:avgRating where b.id =:id")
    void updateRating(@Param("totalRating") long totalRating, @Param("totalVoteCount") long totalVoteCount,  @Param("avgRating") double avgRating, @Param("id") long id);

    //получение текущего количества просмотров книги
    @Query("select b.view_count from Book b where b.id = :id")
    long getViewCountById(@Param("id") long id);

    // Метод для получения текущего суммарного рейтинга книги
    @Query("select b.total_rating from Book b where b.id = :bookId")
    long getTotalRatingById(@Param("bookId") long bookId);

    // Метод для получения текущего количества голосов книги
    @Query("select b.total_vote_count from Book b where b.id = :bookId")
    long getTotalVoteCountById(@Param("bookId") long bookId);

    @Query("SELECT COUNT(b) > 0 FROM Book b WHERE b.name = :bookName")
    boolean existsByNameIgnoreCase(String bookName);

}

