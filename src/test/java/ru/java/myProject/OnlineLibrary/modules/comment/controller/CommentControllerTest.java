package ru.java.myProject.OnlineLibrary.modules.comment.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.comment.dto.CommentDto;
import ru.java.myProject.OnlineLibrary.modules.comment.dto.CommentEditDto;
import ru.java.myProject.OnlineLibrary.modules.comment.dto.mapper.CommentMapper;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.Comment;
import ru.java.myProject.OnlineLibrary.modules.comment.service.CommentServiceImpl;
import ru.java.myProject.OnlineLibrary.modules.user.dto.UserDto;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentServiceImpl commentService;
    @Mock
    private CommentMapper commentMapper;
    @InjectMocks
    private CommentController commentController;


    @Test
    void testGetCommentById() {
        Long commentId = 1L;
        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();

        Mockito.when(commentService.findOneByCommentId(commentId)).thenReturn(comment);
        Mockito.when(commentMapper.convert(comment)).thenReturn(commentDto);

        ResponseEntity<CommentDto> response = commentController.getCommentById(commentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(commentDto, response.getBody());
    }

    @Test
    void testDeleteComment() {
        Long commentId = 1L;

        ResponseEntity<HttpStatus> response = commentController.deleteComment(commentId);

        Mockito.verify(commentService).remove(commentId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testEditComment() throws AccessDeniedException {
        Long commentId = 1L;
        String newText = "Updated comment text";
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("user1");

        CommentEditDto commentEditDto = new CommentEditDto(newText);
        Comment updatedComment = new Comment();
        updatedComment.setText(newText);

        UserDto userDto = new UserDto();
        userDto.setUsername("user1");
        Book book = new Book();
        book.setName("Book Title");

        CommentDto commentDto = new CommentDto(newText, userDto, book);

        Mockito.when(commentService.editComment(commentId, newText, principal)).thenReturn(updatedComment);
        Mockito.when(commentMapper.convert(updatedComment)).thenReturn(commentDto);

        ResponseEntity<CommentDto> response = commentController.editComment(commentId, commentEditDto, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(commentDto, response.getBody());
        Mockito.verify(commentService).editComment(commentId, newText, principal);
    }

}