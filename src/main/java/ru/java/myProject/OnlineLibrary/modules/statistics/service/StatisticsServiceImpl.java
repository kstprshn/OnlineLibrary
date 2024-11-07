package ru.java.myProject.OnlineLibrary.modules.statistics.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.myProject.OnlineLibrary.modules.book.repository.BookRepository;
import ru.java.myProject.OnlineLibrary.modules.comment.repository.CommentRepository;
import ru.java.myProject.OnlineLibrary.modules.vote.repository.VoteRepository;
import ru.java.myProject.OnlineLibrary.util.EntityNotFoundException;

import java.util.NoSuchElementException;

@Service
@Slf4j
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {

    private final BookRepository bookRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public StatisticsServiceImpl(BookRepository bookRepository, VoteRepository voteRepository,
                                 CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.voteRepository = voteRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Long countReadsByBookId(Long bookId) {
        log.info("Counting reads for book with id: {}", bookId);
        try {
            return bookRepository.getViewCountById(bookId);
        } catch (NoSuchElementException e) {
            log.error("Failed to count reads for book with id: {}, error: {}", bookId, e.getMessage());
            throw new EntityNotFoundException("The book with id " + bookId + " NOT FOUND.");
        }
    }

    @Override
    public Integer countVotesByBookId(Long bookId) {
        log.info("Counting votes for book with id: {}", bookId);
        try {
            return voteRepository.countByBookId(bookId);
        } catch (NoSuchElementException e) {
            log.error("Failed to count votes for book with id: {}, error: {}", bookId, e.getMessage());
            throw new EntityNotFoundException("The book with id " + bookId + " NOT FOUND.");
        }
    }

    @Override
    public Integer countCommentsByBookId(Long bookId) {
        log.info("Counting comments for book with id: {}", bookId);
        try {
            return commentRepository.countByBookId(bookId);
        } catch (NoSuchElementException e) {
            log.error("Failed to count comments for book with id: {}, error: {}", bookId, e.getMessage());
            throw new EntityNotFoundException("The book with id " + bookId + " NOT FOUND.");
        }
    }

    @Override
    public Double getAverageRatingByBookId(Long bookId) {
        log.info("Getting average rating for book with id: {}", bookId);
        try {
            return voteRepository.getAverageRatingByBookId(bookId);
        } catch (NoSuchElementException e) {
            log.error("Failed to get average rating for book with id: {}, error: {}", bookId, e.getMessage());
            throw new EntityNotFoundException("The book with id " + bookId + " NOT FOUND.");
        }
    }
}
