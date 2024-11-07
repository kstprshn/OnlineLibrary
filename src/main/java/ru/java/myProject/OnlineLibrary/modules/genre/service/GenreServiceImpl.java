package ru.java.myProject.OnlineLibrary.modules.genre.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.myProject.OnlineLibrary.modules.genre.entity.Genre;
import ru.java.myProject.OnlineLibrary.modules.genre.repository.GenreRepository;
import ru.java.myProject.OnlineLibrary.util.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    @Override
    @Cacheable(value = "genres", key = "#sort")
    public List<Genre> getAll(Sort sort) {
        log.info("Searching all genres with sort: {}", sort);
        return genreRepository.findAll(sort);
    }

    @Override
    public Optional<Genre> findByName(String name) {
        log.info("Searching for genre by name: {}", name);
        return genreRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Genre> search(String... stringToFind) {
        log.info("Searching genres by name part: {}", (Object) stringToFind);
        return genreRepository.findByNameContainingIgnoreCaseOrderByName(stringToFind[0]);
    }


    @Override
    public Genre get(long id) {
        log.info("Searching genre with id: {}", id);
        return genreRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Genre with id " + id + " not found"));
    }

    @Override
    @Transactional
    @CachePut(value = "genres", key = "#genre.id")
    public Genre save(Genre genre) {
        log.info("Saving new genre: {}", genre);
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    @CacheEvict(value = "genres", key = "#id")
    public void delete(long id) {
        log.info("Deleting genre with id: {}", id);
        genreRepository.deleteById(id);
    }
}
