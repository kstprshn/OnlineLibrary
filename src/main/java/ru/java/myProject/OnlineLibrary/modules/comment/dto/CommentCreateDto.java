package ru.java.myProject.OnlineLibrary.modules.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDto {

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotEmpty(message = "Text must not be empty")
    @Size(min = 1, max = 100, message = "Text length must be between 1 and 100 characters")
    private String text;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
