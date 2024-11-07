package ru.java.myProject.OnlineLibrary.modules.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 2, max = 20, message = "Name length must be between 2 and 20 characters")
    private String name;

    @NotEmpty(message = "Description must not be empty")
    @Size(min = 100, max = 400, message = "Description length must be between 100 and 400 characters")
    private String description;

    @NotBlank(message = "ISBN must not be empty or null")
    @Size(min = 7, max = 14, message = "ISBN length must be between 7 and 14 characters")
    private String isbn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
