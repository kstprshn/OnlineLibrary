package ru.java.myProject.OnlineLibrary.modules.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.java.myProject.OnlineLibrary.modules.author.entity.Author;

import java.util.List;

@Repository
@RepositoryRestResource
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByFioContainingIgnoreCaseOrderByFio(String fio);

    @Query("SELECT COUNT(a) > 0 FROM Author a WHERE a.fio = :fio")
    boolean existsByFioIgnoreCase(String fio);

}
