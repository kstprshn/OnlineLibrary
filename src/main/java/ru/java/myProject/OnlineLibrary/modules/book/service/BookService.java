package ru.java.myProject.OnlineLibrary.modules.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.book.entity.enums.SearchType;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface BookService{

    Page<Book> getAll(int pageNumber, int pageSize, String fieldOfSort, Sort.Direction sortDirection);

    Page<Book> search(int pageNumber, int pageSize, String fieldOfSort, Sort.Direction sortDirection, String... stringToFind);

    Page<Book> searchBooksByCriteria(SearchType searchType, String searchValue, int pageNumber, int pageSize, String fieldOfSort, Sort.Direction sortDirection);

    List<Book> findTopBooks(int limit);

    Optional<byte[]> getContent(long id);

    void updateViewCount(long viewCount, long id);

    void updateRating(long id, int rating, Principal principal);

    void updateBook(long id, Book updatedBook);

    long getViewCountById(long id);

    Book get(long id);

    void delete( long id);

    Book save(Book b, MultipartFile image, MultipartFile pdf) throws IOException;

}
