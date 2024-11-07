package ru.java.myProject.OnlineLibrary.modules.comment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.Comment;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.CommentParams;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;
import ru.java.myProject.OnlineLibrary.modules.book.repository.BookRepository;
import ru.java.myProject.OnlineLibrary.modules.comment.repository.CommentRepository;
import ru.java.myProject.OnlineLibrary.modules.user.repository.UserRepository;
import ru.java.myProject.OnlineLibrary.util.EntityNotFoundException;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Comment> findByUserId(Long userId) {
        log.info("Searching comments by user id: {}", userId);
        return commentRepository.findByUserId(userId);
    }
    @Override
    public Page<Comment> findByBookId(Long bookId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Searching comments by book id: {} with pagination, Page: {}, Size: {}", bookId, page, size);
        try {
            return commentRepository.findByBookId(bookId, pageable);
        }catch (NoSuchElementException e){
            throw new EntityNotFoundException("The book with Id " + bookId + " not found.");
        }
    }
    @Override
    public Comment findOneByCommentId(Long commentId) {
        log.info("Searching comment by comment id: {}", commentId);
        return commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Comment with Id " + commentId + " not found."));
    }
    @Override
    @Transactional
    public Comment save(CommentParams commentParams, Principal principal) throws AccessDeniedException {
        log.info("Saving new comment for book id: {} by user: {}", commentParams.getBookId(), principal.getName());
        Book book = bookRepository.findById(commentParams.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Comment savingComment = Comment.builder()
                .user(getUserByPrincipal(principal))
                .book(book)
                .text(commentParams.getCommentText())
                .build();

        log.info("Saving comment: {}", savingComment);
        return commentRepository.save(savingComment);
    }
    public User getUserByPrincipal(Principal principal) throws AccessDeniedException {
        if (principal == null)
            throw new AccessDeniedException("You are not allowed to create this comment");
        return userRepository.findByUsername(principal.getName());
    }
    @Override
    @Transactional
    public Comment editComment(Long commentId, String newText, Principal principal) throws AccessDeniedException {
        log.info("Editing comment with id: {} by user: {}", commentId, principal.getName());
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + commentId + " not found"));

        if (!comment.getUser().getUsername().equals(principal.getName())) {
            log.warn("User: {} is not allowed to edit comment with id: {}", principal.getName(), commentId);
            throw new AccessDeniedException("You are not allowed to edit this comment");
        }
        comment.setText(newText);
        log.info("Comment with id: {} successfully edited by user: {}", commentId, principal.getName());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        log.info("Deleting comment with id: {}", id);
        commentRepository.deleteById(id);
    }

}
