package ru.java.myProject.OnlineLibrary.modules.book.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.java.myProject.OnlineLibrary.modules.book.dto.BookInfoDto;
import ru.java.myProject.OnlineLibrary.modules.book.dto.BookResponse;
import ru.java.myProject.OnlineLibrary.modules.book.dto.mapper.BookMapper;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.book.service.BookServiceImpl;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookServiceImpl bookServiceImpl;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookController bookController;


    @Test
    public void allBooks_ShouldReturnPageOfBookResponses() {

        int pageNumber = 0;
        int pageSize = 10;
        String sortField = "name";
        Sort.Direction sortDirection = Sort.Direction.ASC;
        Book book = new Book();
        BookResponse bookResponse = new BookResponse();

        Page<Book> booksPage = new PageImpl<>(List.of(book));
        Mockito.when(bookServiceImpl.getAll(pageNumber, pageSize, sortField, sortDirection)).thenReturn(booksPage);
        Mockito.when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);
        ResponseEntity<Page<BookResponse>> response = bookController.allBooks(pageNumber, pageSize, sortField, sortDirection);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getContent().size());
        assertEquals(bookResponse, response.getBody().getContent().get(0));
    }

    @Test
    public void showBookInfo_ShouldReturnBookInfoDto() {
        Long bookId = 1L;
        Book book = new Book();
        BookInfoDto bookInfoDto = new BookInfoDto();

        Mockito.when(bookServiceImpl.get(bookId)).thenReturn(book);

        Mockito.when(bookMapper.toBookInfoDto(book)).thenReturn(bookInfoDto);

        ResponseEntity<BookInfoDto> response = bookController.showBookInfo(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookInfoDto, response.getBody());
        Mockito.verify(bookServiceImpl).get(bookId);
    }
}