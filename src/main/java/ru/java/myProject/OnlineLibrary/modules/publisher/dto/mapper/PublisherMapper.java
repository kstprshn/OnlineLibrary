package ru.java.myProject.OnlineLibrary.modules.publisher.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapping;
import ru.java.myProject.OnlineLibrary.modules.book.dto.mapper.BookMapper;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.PublisherBooksDto;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.PublisherRequestDto;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.PublisherResponseDto;
import ru.java.myProject.OnlineLibrary.modules.publisher.entity.Publisher;

@Component
@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface PublisherMapper {

    @Mapping(target = "name", source = "name")
    Publisher convert (PublisherRequestDto publisherRequestDto);

    @Mapping(target = "name", source = "name")
    PublisherResponseDto convertToDto (Publisher publisher);

    @Mapping(target = "books", source = "books")
    @Mapping(target = "name", source = "name")
    PublisherBooksDto convert (Publisher publisher);

}
