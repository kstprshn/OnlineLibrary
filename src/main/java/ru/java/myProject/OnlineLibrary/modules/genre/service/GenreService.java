package ru.java.myProject.OnlineLibrary.modules.genre.service;


import org.springframework.data.domain.Sort;
import ru.java.myProject.OnlineLibrary.modules.genre.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService{

    List<Genre> getAll(Sort sort);

    List<Genre> search(String... stringToFind);

    Optional<Genre> findByName(String name);

    Genre get(long id);

    Genre save(Genre obj);

    void delete(long id);
}
