package ru.java.myProject.OnlineLibrary.modules.author.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.myProject.OnlineLibrary.modules.author.entity.Author;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.author.repository.AuthorRepository;
import ru.java.myProject.OnlineLibrary.util.EntityAlreadyExistsException;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Author> getAll(Sort sort) {
        log.info("Searching all authors with sort: {}", sort);
        return authorRepository.findAll(sort);
    }

    @Override
    public List<Author> search(String... stringToFind) {
        log.info("Searching authors by fio part: {}", (Object) stringToFind);
        return authorRepository.findByFioContainingIgnoreCaseOrderByFio(stringToFind[0]);
    }

    @Override
    public Author get(long id) {
        log.info("Searching author with id: {}", id);
        return authorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Author with id " + id + " not found"));
    }

    @Override
    public List<Book> showBooksByAuthorId(Long authorId){

        log.info("Searching books for author with id: {}", authorId);
        Author author = authorRepository.findById(authorId).orElseThrow(
                () -> new EntityNotFoundException("Author with id " + authorId + " not found"));
        return author.getBooks();
    }

    @Override
    @Transactional
    public Author save(Author author) {
        if (authorRepository.existsByFioIgnoreCase(author.getFio())) {
            log.error("Author with FIO {} already exists", author.getFio());
            throw new EntityAlreadyExistsException("Author with fio " + author.getFio() + " already exists");
        }
        log.info("Saving new author: {}", author);
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("Deleting author with id: {}", id);
        authorRepository.deleteById(id);
    }

}
