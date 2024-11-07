package ru.java.myProject.OnlineLibrary.modules.author.service;

import org.springframework.data.domain.Sort;
import ru.java.myProject.OnlineLibrary.modules.author.entity.Author;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;

import java.util.List;


public interface AuthorService{

    List<Author> getAll(Sort sort);

    List<Book> showBooksByAuthorId(Long authorId);

    List<Author> search(String... stringToFind);

    Author get(long id);

    Author save(Author a);

    void delete(long id);

}
