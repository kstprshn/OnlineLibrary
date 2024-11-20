package ru.java.myProject.OnlineLibrary.modules.genre.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.java.myProject.OnlineLibrary.modules.genre.dto.GenreBooksDto;
import ru.java.myProject.OnlineLibrary.modules.genre.dto.GenreDto;
import ru.java.myProject.OnlineLibrary.modules.genre.dto.mapper.GenreMapper;
import ru.java.myProject.OnlineLibrary.modules.genre.entity.Genre;
import ru.java.myProject.OnlineLibrary.modules.genre.service.GenreServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genre")
@Tag(name = "Genre API", description = "Methods of genre management")
public class GenreController {

    private final GenreServiceImpl genreServiceImpl;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreController(GenreServiceImpl genreServiceImpl, GenreMapper genreMapper) {
        this.genreServiceImpl = genreServiceImpl;
        this.genreMapper = genreMapper;
    }

    @GetMapping("/search")
    @Operation(summary = "Search genres")
    public ResponseEntity<List<GenreBooksDto>> searchGenres(@RequestBody @NotEmpty String genreName) {
        List<Genre> genres = genreServiceImpl.search(genreName);
        if (genres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<GenreBooksDto> genresDto = genres.stream().map(genreMapper::convert)
                .collect(Collectors.toList());
        return ResponseEntity.ok(genresDto);
    }

    @GetMapping("/allGenres")
    @Operation(summary = "Get all genres")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<Genre> genres = genreServiceImpl.getAll(Sort.by(Sort.Direction.ASC, "name"));
        if (genres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<GenreDto> genresDto = genres.stream().map(genreMapper::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(genresDto);
    }

    @GetMapping("/findOne/{genre_id}")
    @Operation(summary = "Get genre by ID")
    public ResponseEntity<GenreBooksDto> getGenreById(@PathVariable("genre_id") Long id) {
        Genre genre = genreServiceImpl.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(genreMapper.convert(genre));
    }

    @PostMapping("/create")
    @Operation(summary = "Add a genre")
    public ResponseEntity<HttpStatus> createGenre(@RequestBody @Valid GenreDto genreDto) {
        if (genreServiceImpl.findByName(genreDto.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        genreServiceImpl.save(genreMapper.convert(genreDto));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{genre_id}")
    @Operation(summary = "Delete a genre")
    public ResponseEntity<HttpStatus> removeGenre(@PathVariable("genre_id") Long id) {
        genreServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
}

