package ru.java.myProject.OnlineLibrary.modules.book.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.java.myProject.OnlineLibrary.modules.author.dto.AuthorDto;

@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private String name;
    private byte[] image;
    private Long total_vote_count;
    private Double avg_rating;
    private AuthorDto author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getTotal_vote_count() {
        return total_vote_count;
    }

    public void setTotal_vote_count(Long total_vote_count) {
        this.total_vote_count = total_vote_count;
    }

    public Double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(Double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }
}
