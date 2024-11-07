package ru.java.myProject.OnlineLibrary.modules.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.myProject.OnlineLibrary.modules.statistics.service.StatisticsServiceImpl;


@RestController
@RequestMapping("/api/admin/statistics")
public class StatisticsController {

    private final StatisticsServiceImpl statisticsServiceImpl;

    @Autowired
    public StatisticsController(StatisticsServiceImpl statisticsServiceImpl) {
        this.statisticsServiceImpl = statisticsServiceImpl;
    }

    @GetMapping("/total-views-counter/{book_id}")
    public ResponseEntity<Long> getViewsByBookId(@PathVariable("book_id") Long bookId) {
            long viewsCount = statisticsServiceImpl.countReadsByBookId(bookId);
            return new ResponseEntity<>(viewsCount, HttpStatus.OK);
    }

    @GetMapping("/total-votes-counter/{book_id}")
    public ResponseEntity<Long> getVotesByBookId(@PathVariable("book_id") Long bookId) {
            long voteCount = statisticsServiceImpl.countVotesByBookId(bookId);
            return new ResponseEntity<>(voteCount, HttpStatus.OK);
    }

    @GetMapping("/total-comments-counter/{bookId}")
    public ResponseEntity<Long> getCommentsByBookId(@PathVariable("bookId") Long bookId) {
            long commentCount = statisticsServiceImpl.countCommentsByBookId(bookId);
            return new ResponseEntity<>(commentCount, HttpStatus.OK);
    }

    @GetMapping("/average-rating/{book_id}")
    public ResponseEntity<Double> getAverageRatingByBookId(@PathVariable("book_id") Long bookId) {
            Double averageRating = statisticsServiceImpl.getAverageRatingByBookId(bookId);
            if (averageRating != null) {
                return new ResponseEntity<>(averageRating, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
    }
}
