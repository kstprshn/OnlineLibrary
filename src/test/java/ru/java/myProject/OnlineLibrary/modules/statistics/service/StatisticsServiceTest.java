package ru.java.myProject.OnlineLibrary.modules.statistics.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.java.myProject.OnlineLibrary.modules.book.repository.BookRepository;
import ru.java.myProject.OnlineLibrary.modules.comment.repository.CommentRepository;
import ru.java.myProject.OnlineLibrary.modules.vote.repository.VoteRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;


    @Test
    void testCountReadsByBookId() {
        Long bookId = 1L;
        Long viewCount = 50L;

        Mockito.when(bookRepository.getViewCountById(bookId)).thenReturn(viewCount);

        Long result = statisticsService.countReadsByBookId(bookId);
        assertEquals(viewCount, result);
    }

    @Test
    void testCountVotesByBookId() {
        Long bookId = 1L;
        Integer votesCount = 10;

        Mockito.when(voteRepository.countByBookId(bookId)).thenReturn(votesCount);

        Integer result = statisticsService.countVotesByBookId(bookId);
        assertEquals(votesCount, result);
    }

    @Test
    void testCountCommentsByBookId() {
        Long bookId = 1L;
        int commentsCount = 15;

        Mockito.when(commentRepository.countByBookId(bookId)).thenReturn(commentsCount);

        Integer result = statisticsService.countCommentsByBookId(bookId);
        assertEquals(commentsCount, result);
    }

    @Test
    void testGetAverageRatingByBookId() {
        Long bookId = 1L;
        Double avgRating = 4.5;

        Mockito.when(voteRepository.getAverageRatingByBookId(bookId)).thenReturn(avgRating);

        Double result = statisticsService.getAverageRatingByBookId(bookId);
        assertEquals(avgRating, result);
    }
}