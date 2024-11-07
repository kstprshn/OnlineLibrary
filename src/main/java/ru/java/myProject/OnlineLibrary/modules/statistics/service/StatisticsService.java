package ru.java.myProject.OnlineLibrary.modules.statistics.service;

public interface StatisticsService {

    Long countReadsByBookId(Long bookId);
    Integer countVotesByBookId(Long bookId);

    Integer countCommentsByBookId(Long bookId);
    Double getAverageRatingByBookId(Long bookId);

}
