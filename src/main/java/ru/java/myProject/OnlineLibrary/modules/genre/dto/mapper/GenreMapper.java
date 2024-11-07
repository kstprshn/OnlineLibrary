package ru.java.myProject.OnlineLibrary.modules.genre.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.java.myProject.OnlineLibrary.modules.book.dto.mapper.BookMapper;
import ru.java.myProject.OnlineLibrary.modules.genre.dto.GenreBooksDto;
import ru.java.myProject.OnlineLibrary.modules.genre.dto.GenreDto;
import ru.java.myProject.OnlineLibrary.modules.genre.entity.Genre;

@Component
@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface GenreMapper {

    @Mapping(target = "name", source = "name")
    Genre convert(GenreDto genreDto);

    @Mapping(target = "name", source = "name")
    GenreDto convertToDto(Genre genre);

    @Mapping(target = "books", source = "books")
    GenreBooksDto convert(Genre genre);
}
