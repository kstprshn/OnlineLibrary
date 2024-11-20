package ru.java.myProject.OnlineLibrary.modules.author.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.java.myProject.OnlineLibrary.modules.author.dto.AuthorRequestDto;
import ru.java.myProject.OnlineLibrary.modules.author.dto.AuthorResponseDto;
import ru.java.myProject.OnlineLibrary.modules.book.dto.BookResponse;
import ru.java.myProject.OnlineLibrary.modules.author.dto.mapper.AuthorMapper;
import ru.java.myProject.OnlineLibrary.modules.book.dto.mapper.BookMapper;
import ru.java.myProject.OnlineLibrary.modules.author.entity.Author;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.author.service.AuthorServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/author")
@Tag(name = "Author API", description = "Methods of author management")
public class AuthorController {

    private final AuthorServiceImpl authorServiceImpl;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @Autowired
    public AuthorController(AuthorServiceImpl authorServiceImpl, AuthorMapper authorMapper, BookMapper bookMapper) {
        this.authorServiceImpl = authorServiceImpl;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @GetMapping("/findAuthor/{author_id}")
    @Operation(summary = "Get the author by Id")
    public ResponseEntity<AuthorResponseDto> getAuthorById(@PathVariable("author_id") Long id) {
        Author author = authorServiceImpl.get(id);
        return author != null ? ResponseEntity.ok(authorMapper.convert(author)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/showBooksByAuthor/{authorId}")
    @Operation(summary = "Show the author's books")
    public ResponseEntity<List<BookResponse>> showAuthorBooks(@PathVariable("authorId") long id) {
        List<Book> authorBooks = authorServiceImpl.showBooksByAuthorId(id);
        return ResponseEntity.ok().body(authorBooks.stream().map(bookMapper::toBookResponse).toList());
    }

    @GetMapping("/findAll")
    @Operation(summary = "Get for authors")
    public ResponseEntity<List<AuthorResponseDto>> getAllAuthors() {
        List<Author> authors = authorServiceImpl.getAll(Sort.by(Sort.Direction.ASC, "fio"));
        return ResponseEntity.ok(authors.stream().map(authorMapper::convert).collect(Collectors.toList()));
    }

    @GetMapping("/search")
    @Operation(summary = "Search for authors")
    public ResponseEntity<List<AuthorResponseDto>> searchAuthors(@RequestBody @Valid AuthorRequestDto authorRequestDto) {
        List<Author> authors = authorServiceImpl.search(authorRequestDto.getFio());
        return ResponseEntity.ok(authors.stream().map(authorMapper::convert).collect(Collectors.toList()));
    }

    @PostMapping("/create")
    @Operation(summary = "Add the author")
    public ResponseEntity<HttpStatus> createAuthor(@RequestBody @Valid AuthorRequestDto authorRequestDto) {
        Author author = authorMapper.convert(authorRequestDto);
        authorServiceImpl.save(author);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{author_id}")
    @Operation(summary = "Delete the author")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("author_id") Long id) {
        authorServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

}
