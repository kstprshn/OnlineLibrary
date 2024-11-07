package ru.java.myProject.OnlineLibrary.modules.book.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDto {

    @NotBlank(message = "Name must not be null or empty")
    @Size(min = 2, max = 20, message = "Name length must be between 2 and 20 characters")
    private String name;

    @NotNull(message = "Description is required")
    @Size(min = 100, max = 400, message = "Description length must be between 100 and 400 characters")
    private String description;

    @NotNull(message = "Page count is required")
    @Min(value = 2, message = "Page count must be at least 2")
    private Integer page_count;

    @NotNull(message = "Publish year is required")
    @Min(value = 1000, message = "Publish year must be a valid year")
    @Max(value = 2024, message = "Publish year must not exceed the current year")
    private Integer publish_year;

    @NotBlank(message = "ISBN must not be blank")
    @Size(min = 7, max = 14, message = "ISBN length must be between 7 and 14 characters")
    private String isbn;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    @NotNull(message = "Genre ID is required")
    private Long genreId;

    @NotNull(message = "Publisher ID is required")
    private Long publisherId;

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

    public Integer getPage_count() {
        return page_count;
    }

    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    public Integer getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(Integer publish_year) {
        this.publish_year = publish_year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }
}
