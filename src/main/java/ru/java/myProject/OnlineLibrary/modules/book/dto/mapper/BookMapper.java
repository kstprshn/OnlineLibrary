package ru.java.myProject.OnlineLibrary.modules.book.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.author.dto.mapper.AuthorMapper;
import ru.java.myProject.OnlineLibrary.modules.book.dto.*;
import ru.java.myProject.OnlineLibrary.modules.genre.dto.mapper.GenreMapper;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.mapper.PublisherMapper;

@Component
@Mapper(componentModel = "spring", uses = {AuthorMapper.class, GenreMapper.class, PublisherMapper.class})
public interface BookMapper {

    @Mapping(target = "name", source = "name")
    Book convert(BooksNameDto booksNameDto);

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "genreId", target = "genre.id")
    @Mapping(source = "publisherId", target = "publisher.id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "descr")
    @Mapping(source = "page_count", target = "page_count")
    @Mapping(source = "publish_year", target = "publish_year")
    @Mapping(source = "isbn", target = "isbn")
    Book convert(BookCreateDto bookCreateDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "descr")
    @Mapping(source = "isbn", target = "isbn")
    Book convert(BookUpdateDto bookUpdateDto);



    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "descr", target = "descr")
    @Mapping(source = "page_count", target = "page_count")
    @Mapping(source = "publish_year", target = "publish_year")
    @Mapping(source = "author.fio", target = "authorName")
    @Mapping(source = "genre.name", target = "genreName")
    @Mapping(source = "publisher.name", target = "publisherName")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "total_vote_count", target = "total_vote_count")
    @Mapping(source = "avg_rating", target = "avg_rating")
    BookInfoDto toBookInfoDto(Book book);

    @InheritInverseConfiguration
    Book convert (BookInfoDto bookInfoDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "total_vote_count", target = "total_vote_count")
    @Mapping(source = "avg_rating", target = "avg_rating")
    @Mapping(source = "author", target = "author")
    BookResponse toBookResponse(Book book);

}
