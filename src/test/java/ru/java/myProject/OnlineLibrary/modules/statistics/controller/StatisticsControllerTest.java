package ru.java.myProject.OnlineLibrary.modules.statistics.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.java.myProject.OnlineLibrary.modules.statistics.service.StatisticsServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StatisticsControllerTest {

    @Mock
    private StatisticsServiceImpl statisticsServiceImpl;

    @InjectMocks
    private StatisticsController statisticsController;

    @Test
    public void getViewsByBookId_ShouldReturnViewsCount() {
        Long bookId = 1L;
        long viewsCount = 100;

        Mockito.when(statisticsServiceImpl.countReadsByBookId(bookId)).thenReturn(viewsCount);

        ResponseEntity<Long> response = statisticsController.getViewsByBookId(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(viewsCount, response.getBody());
    }

    @Test
    public void getVotesByBookId_ShouldReturnVotesCount() {
        Long bookId = 1L;
        Integer voteCount = 50;

        Mockito.when(statisticsServiceImpl.countVotesByBookId(bookId)).thenReturn(voteCount);

        ResponseEntity<Long> response = statisticsController.getVotesByBookId(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Long.valueOf(voteCount), response.getBody());
    }

    @Test
    public void getCommentsByBookId_ShouldReturnCommentsCount() {
        Long bookId = 1L;
        Integer commentCount = 20;

        Mockito.when(statisticsServiceImpl.countCommentsByBookId(bookId)).thenReturn(commentCount);

        ResponseEntity<Long> response = statisticsController.getCommentsByBookId(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Long.valueOf(commentCount), response.getBody());
    }

    @Test
    public void getAverageRatingByBookId_ShouldReturnAverageRating() {
        Long bookId = 1L;
        Double averageRating = 4.5;

        Mockito.when(statisticsServiceImpl.getAverageRatingByBookId(bookId)).thenReturn(averageRating);

        ResponseEntity<Double> response = statisticsController.getAverageRatingByBookId(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(averageRating, response.getBody());
    }

    @Test
    public void getAverageRatingByBookId_ShouldReturnNotFoundIfRatingIsNull() {
        Long bookId = 1L;

        Mockito.when(statisticsServiceImpl.getAverageRatingByBookId(bookId)).thenReturn(null);

        ResponseEntity<Double> response = statisticsController.getAverageRatingByBookId(bookId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
