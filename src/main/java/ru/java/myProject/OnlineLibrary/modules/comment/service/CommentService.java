package ru.java.myProject.OnlineLibrary.modules.comment.service;

import org.springframework.data.domain.Page;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.Comment;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.CommentParams;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

public interface CommentService {

    List<Comment> findByUserId(Long userId);

    Page<Comment> findByBookId(Long bookId, int page, int size);

    Comment findOneByCommentId(Long commentId);

    Comment save(CommentParams commentParams, Principal principal) throws AccessDeniedException;

    void remove(Long id);

    Comment editComment(Long commentId, String newText, Principal principal) throws AccessDeniedException;
}
