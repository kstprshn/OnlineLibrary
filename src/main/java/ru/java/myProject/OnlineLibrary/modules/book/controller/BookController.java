package ru.java.myProject.OnlineLibrary.modules.book.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.java.myProject.OnlineLibrary.modules.book.dto.mapper.BookMapper;
import ru.java.myProject.OnlineLibrary.modules.book.dto.*;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.vote.dto.VoteDto;
import ru.java.myProject.OnlineLibrary.modules.book.service.BookServiceImpl;
import ru.java.myProject.OnlineLibrary.modules.book.validation.BookDataValidator;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book")
public class BookController {

    public static final int TOP_BOOKS_LIMIT = 5;

    private final BookServiceImpl bookServiceImpl;
    private final BookMapper bookMapper;

    private final BookDataValidator bookDataValidator;


    @Autowired
    public BookController(BookServiceImpl bookServiceImpl, BookMapper bookMapper, BookDataValidator bookDataValidator) {
        this.bookServiceImpl = bookServiceImpl;
        this.bookMapper = bookMapper;
        this.bookDataValidator = bookDataValidator;
    }

    @GetMapping("/allBooks")
    public ResponseEntity<Page<BookResponse>> allBooks(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                       @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                       @RequestParam(defaultValue = "name", required = false) String sortField,
                                                       @RequestParam(defaultValue = "ASC", required = false) Sort.Direction sortDirection) {

        Page<Book> listOfBooks = bookServiceImpl.getAll(pageNumber, pageSize, sortField, sortDirection);
        return ResponseEntity.ok().body(listOfBooks.map(bookMapper::toBookResponse));
    }

    @GetMapping("/bookInfo/{book_id}")
    public ResponseEntity<BookInfoDto> showBookInfo(@PathVariable("book_id") Long id) {
        Book book = bookServiceImpl.get(id);
        BookInfoDto bookInfo = bookMapper.toBookInfoDto(book);
        return ResponseEntity.ok().body(bookInfo);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookResponse>> searchBooks(@RequestBody BookSearchTypeDto searchType,
                                                          @RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                          @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                          @RequestParam(defaultValue = "name", required = false) String sortField,
                                                          @RequestParam(defaultValue = "ASC", required = false) Sort.Direction sortDirection) {

        Page<Book> books = bookServiceImpl.searchBooksByCriteria(
                searchType.getSearchType(), searchType.getSearchValue(),
                pageNumber, pageSize, sortField, sortDirection);
        return ResponseEntity.ok().body(books.map(bookMapper::toBookResponse));
    }

    @GetMapping("/topBooks")
    public ResponseEntity<List<BookResponse>> getTopBooks() {

        List<BookResponse> topBooks = bookServiceImpl.findTopBooks(TOP_BOOKS_LIMIT)
                .stream().map(bookMapper::toBookResponse).toList();
        return ResponseEntity.ok().body(topBooks);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createBook(@RequestPart("book") @Valid BookCreateDto bookDto,
                                                 BindingResult bindingResult,
                                                 @RequestPart(value = "pdf", required = false) MultipartFile pdfFile,
                                                 @RequestPart(value = "image") MultipartFile imageFile) throws IOException {

        Book book = bookMapper.convert(bookDto);
        bookDataValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMessages);
        }
        bookServiceImpl.save(book, imageFile, pdfFile);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book created successfully");
    }

    @PutMapping(value = "/redact/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> updateBook(@PathVariable("id") long bookToUpdateId,
                                                 @RequestPart("book") @Valid BookUpdateDto updatedBookDto) {
        Book updatedBook = bookMapper.convert(updatedBookDto);
        bookServiceImpl.updateBook(bookToUpdateId, updatedBook);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/voting")
    public ResponseEntity<String> voting(@RequestBody @Valid VoteDto voteDto, Principal principal) {
        bookServiceImpl.updateRating(voteDto.getBookId(), voteDto.getRating(), principal);
        return ResponseEntity.ok("Your vote is written.");
    }

    @DeleteMapping("/delete/{book_id}")
    public ResponseEntity<HttpStatus> removeBook(@PathVariable("book_id") long id) {
        bookServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
