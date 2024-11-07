package ru.java.myProject.OnlineLibrary.modules.book.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.java.myProject.OnlineLibrary.modules.author.entity.Author;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.Comment;
import ru.java.myProject.OnlineLibrary.modules.genre.entity.Genre;
import ru.java.myProject.OnlineLibrary.modules.publisher.entity.Publisher;
import ru.java.myProject.OnlineLibrary.modules.vote.entity.Vote;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
@NoArgsConstructor
@DynamicUpdate
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Column(name = "content")
    private byte[] content;

    @Column(name = "page_count")
    private Integer page_count;

    @Column(name = "isbn", unique = true)
    private String  isbn;

    @Column(name = "publish_year")
    private Integer publish_year;

    @JsonIgnore
    @Column(name="image")
    private byte[] image;

    @Column(name="descr")
    private String descr;

    @Column(name = "view_count")
    private Long view_count;

    @Column(name = "total_rating")
    private Long total_rating;

    @Column(name = "total_vote_count")
    private Long total_vote_count;

    @Column(name = "avg_rating")
    private Double avg_rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Publisher publisher;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Vote> votes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Integer getPage_count() {
        return page_count;
    }

    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(Integer publish_year) {
        this.publish_year = publish_year;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Long getView_count() {
        return view_count;
    }

    public void setView_count(Long view_count) {
        this.view_count = view_count;
    }

    public Long getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(Long total_rating) {
        this.total_rating = total_rating;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }


    @JsonCreator
    public Book(Long id, String name, Integer page_count, String isbn, Genre genre, Author author,
                Publisher publisher, Integer publish_year, byte[] image, String descr,
                Long view_count, Long total_rating, Long total_vote_count, Double avg_rating) {
        this.id = id;
        this.name = name;
        this.page_count = page_count;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
        this.publish_year = publish_year;
        this.image = image;
        this.descr = descr;
        this.view_count = view_count;
        this.total_rating = total_rating;
        this.total_vote_count = total_vote_count;
        this.avg_rating = avg_rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (view_count != book.view_count) return false;
        if (total_rating != book.total_rating) return false;
        if (total_vote_count != book.total_vote_count) return false;
        if (avg_rating != book.avg_rating) return false;
        if (!Objects.equals(id, book.id)) return false;
        if (!Objects.equals(name, book.name)) return false;
        if (!Objects.equals(page_count, book.page_count)) return false;
        if (!Objects.equals(isbn, book.isbn)) return false;
        if (!Objects.equals(publish_year, book.publish_year)) return false;
        return Objects.equals(descr, book.descr);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (page_count != null ? page_count.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (publish_year != null ? publish_year.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        result = 31 * result + (view_count != null ? view_count.hashCode() : 0);
        result = 31 * result + (total_rating != null ? total_rating.hashCode() : 0);
        result = 31 * result + (total_vote_count != null ? total_vote_count.hashCode() : 0);
        result = 31 * result + (avg_rating != null ? avg_rating.hashCode() : 0);
        return result;
    }

    @Override
    public String   toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", page_count=" + page_count +
                ", isbn='" + isbn + '\'' +
                ", publish_year=" + publish_year +
                ", descr='" + descr + '\'' +
                ", view_count=" + view_count +
                ", total_rating=" + total_rating +
                ", total_vote_count=" + total_vote_count +
                ", avg_rating=" + avg_rating +
                '}';
    }
}
