package ru.java.myProject.OnlineLibrary.modules.author.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "author")
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fio")
    private String fio;

    @Column(name = "birthday")
    private Date birthday;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (!Objects.equals(id, author.id)) return false;
        if (!Objects.equals(fio, author.fio)) return false;
        return Objects.equals(birthday, author.birthday);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
