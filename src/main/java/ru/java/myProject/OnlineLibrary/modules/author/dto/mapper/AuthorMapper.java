package ru.java.myProject.OnlineLibrary.modules.author.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.java.myProject.OnlineLibrary.modules.author.dto.AuthorDto;
import ru.java.myProject.OnlineLibrary.modules.author.dto.AuthorRequestDto;
import ru.java.myProject.OnlineLibrary.modules.author.dto.AuthorResponseDto;
import ru.java.myProject.OnlineLibrary.modules.author.entity.Author;

@Component
@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "fio", source = "fio")
    @Mapping(target = "birthday", source = "birthday")
    Author convert(AuthorRequestDto authorRequestDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fio", source = "fio")
    @Mapping(target = "birthday", source = "birthday")
    AuthorResponseDto convert(Author author);

    @Mapping(target = "fio", source = "fio")
    AuthorDto convertToDto(Author author);
}