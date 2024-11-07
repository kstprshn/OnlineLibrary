package ru.java.myProject.OnlineLibrary.modules.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.Comment;

import java.util.List;

@Repository
@RepositoryRestResource
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByBookId(Long bookId, Pageable pageable);

    List<Comment> findByUserId(Long userId);

    Integer countByBookId(Long bookId);

}
