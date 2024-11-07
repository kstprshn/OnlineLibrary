package ru.java.myProject.OnlineLibrary.modules.book.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.book.repository.BookRepository;
import ru.java.myProject.OnlineLibrary.modules.user.repository.UserRepository;
import ru.java.myProject.OnlineLibrary.modules.vote.repository.VoteRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    void testGetAll() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name"));
        Book book1 = new Book();
        book1.setName("Book1");
        Book book2 = new Book();
        book2.setName("Book2");

        Page<Book> expectedPage = new PageImpl<>(List.of(book1, book2));
        Mockito.when(bookRepository.findAllWithoutContent(pageRequest)).thenReturn(expectedPage);

        Page<Book> result = bookService.getAll(0, 5, "name", Sort.Direction.ASC);
        assertEquals(expectedPage, result);
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        book.setName("Book1");
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.get(1L);
        assertEquals(book, result);
    }
}