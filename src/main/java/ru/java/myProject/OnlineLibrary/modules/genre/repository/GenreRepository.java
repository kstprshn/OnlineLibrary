package ru.java.myProject.OnlineLibrary.modules.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.java.myProject.OnlineLibrary.modules.genre.entity.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByNameIgnoreCase(String name);

    List<Genre> findByNameContainingIgnoreCaseOrderByName(String name);


}
