package ru.java.myProject.OnlineLibrary.modules.genre.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.java.myProject.OnlineLibrary.modules.book.dto.BooksNameDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GenreBooksDto {

    private String name;
    private List<BooksNameDto> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BooksNameDto> getBooks() {
        return books;
    }

    public void setBooks(List<BooksNameDto> books) {
        this.books = books;
    }
}
