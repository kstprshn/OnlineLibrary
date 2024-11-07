package ru.java.myProject.OnlineLibrary.modules.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.java.myProject.OnlineLibrary.modules.user.dto.UserDto;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;

@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotBlank(message = "Text must not be null or empty")
    @Size(min = 1, max = 100, message = "Text length must be between 1 and 100 characters")
    private String text;

    @NotNull(message = "User is required")
    private UserDto user;

    @NotNull(message = "Book is required")
    private Book book;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
