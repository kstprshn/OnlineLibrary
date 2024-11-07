package ru.java.myProject.OnlineLibrary.modules.publisher.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.java.myProject.OnlineLibrary.modules.book.repository.BookRepository;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.Comment;
import ru.java.myProject.OnlineLibrary.modules.comment.repository.CommentRepository;
import ru.java.myProject.OnlineLibrary.modules.comment.service.CommentServiceImpl;
import ru.java.myProject.OnlineLibrary.modules.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CommentServiceImpl commentService;


    @Test
    public void findByUserId_ShouldReturnListOfComments() {
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());
        Mockito.when(commentRepository.findByUserId(1L)).thenReturn(comments);

        List<Comment> result = commentService.findByUserId(1L);

        assertEquals(2, result.size());
    }

    @Test
    public void findOneByCommentId_ShouldReturnComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment result = commentService.findOneByCommentId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void remove_ShouldDeleteComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.remove(1L);

        Mockito.verify(commentRepository).deleteById(1L);
    }
}