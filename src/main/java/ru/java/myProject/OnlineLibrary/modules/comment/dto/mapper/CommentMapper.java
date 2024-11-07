package ru.java.myProject.OnlineLibrary.modules.comment.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapping;
import ru.java.myProject.OnlineLibrary.modules.comment.dto.CommentCreateDto;
import ru.java.myProject.OnlineLibrary.modules.comment.dto.CommentDto;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.Comment;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.CommentParams;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper{

    @Mapping(target = "text", source = "text")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "book", source = "book")
    CommentDto convert(Comment comment);

    @InheritInverseConfiguration
    Comment convert(CommentDto commentDto);

    @Mapping(target = "bookId", source = "bookId")
    @Mapping(target = "commentText", source = "text")
    CommentParams convertToParams(CommentCreateDto commentCreateDto);

    @Mapping(target = "commentText", source = "text")
    @Mapping(target = "bookId", source = "book.id")
    CommentParams convertToParams(Comment comment);
}