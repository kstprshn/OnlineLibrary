package ru.java.myProject.OnlineLibrary.modules.book.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;
import ru.java.myProject.OnlineLibrary.modules.vote.entity.Vote;
import ru.java.myProject.OnlineLibrary.modules.book.entity.enums.SearchType;
import ru.java.myProject.OnlineLibrary.modules.book.repository.BookRepository;
import ru.java.myProject.OnlineLibrary.modules.user.repository.UserRepository;
import ru.java.myProject.OnlineLibrary.modules.vote.repository.VoteRepository;
import ru.java.myProject.OnlineLibrary.util.EntityAlreadyExistsException;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static ru.java.myProject.OnlineLibrary.modules.book.entity.enums.SearchType.*;

@Service
@Transactional(readOnly = true)
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository, VoteRepository voteRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Cacheable("books")
    public Page<Book> getAll(int pageNumber, int pageSize, String fieldOfSort,
                             Sort.Direction sortDirection) {
        log.info("Getting books with pagination. Page: {}, Size: {}, Sort Field: {}, Sort Direction: {}",
                                                        pageNumber, pageSize, fieldOfSort, sortDirection);
        return bookRepository.findAllWithoutContent(PageRequest.of(pageNumber, pageSize,
                Sort.by(sortDirection, fieldOfSort)));
    }

    @Override
    public Page<Book> search(int pageNumber, int pageSize, String fieldOfSort,
                             Sort.Direction sortDirection, String... stringToFind) {
        log.info("Searching for books by author name part: {}, Page: {}, Size: {}, Sort: {}",
                stringToFind[0], pageNumber, pageSize, fieldOfSort);
        String fioToFind = stringToFind[0].isEmpty() ? null : stringToFind[0];
        return bookRepository.findByAuthorFioContainingIgnoreCase(
                fioToFind, PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, fieldOfSort))).orElse(null);
    }

    @Override
    public Page<Book> searchBooksByCriteria(SearchType searchType, String searchValue, int page, int size,
                                            String sortField, Sort.Direction sortDirection) {

        if(searchType == null || searchType.toString().trim().isEmpty())
            searchType = ALL;

        log.info("Searching books by criteria: {}, value: {}", searchType, searchValue);
        switch (searchType) {

            case SEARCH_AUTHOR -> {
                if (searchValue == null || searchValue.trim().isEmpty()) {
                    throw new IllegalArgumentException("Author FIO must be provided.");
                }
                log.info("Searching books by author: {}", searchValue);
                return bookRepository.findByAuthorFioContainingIgnoreCase(
                                searchValue, PageRequest.of(page, size, Sort.by(sortDirection, sortField)))
                        .orElseThrow(() -> new EntityNotFoundException("Author not found"));
            }
            case SEARCH_GENRE -> {
                if (searchValue == null || searchValue.trim().isEmpty()) {
                    throw new IllegalArgumentException("Genre name must be provided.");
                }
                log.info("Searching books by genre: {}", searchValue);
                return bookRepository.findByGenreName(searchValue, PageRequest.of(page, size, Sort.by(sortDirection, sortField)))
                        .orElseThrow(() -> new EntityNotFoundException("Genre not found"));
            }
            case SEARCH_TEXT -> {
                if (searchValue == null || searchValue.trim().isEmpty()) {
                    throw new IllegalArgumentException("Book name must be provided.");
                }
                log.info("Searching books by name: {}", searchValue);
                List<Book> booksByName = bookRepository.findByNameContainingIgnoreCaseOrderByName(searchValue)
                        .orElseThrow(() -> new EntityNotFoundException("Book not found"));
                return new PageImpl<>(booksByName, PageRequest.of(page, size, Sort.by(sortDirection, sortField)), booksByName.size());
            }
            case ALL -> {
                log.info("Searching all books");
                return bookRepository.findAllWithoutContent(PageRequest.of(page, size,
                        Sort.by(sortDirection, sortField)));
            }
            default -> throw new IllegalArgumentException("Invalid search criteria");
        }
    }

    @Override
    @Cacheable(value = "bookInfo", key = "#id")
    public Book get(long id) {
        log.info("Getting book by id: {}", id);
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id " + id + " not found"));
    }

    @Override
    @Transactional
    @CachePut(value = "books", key = "#book.id")
    public Book save(Book book,MultipartFile imageFile, MultipartFile pdfFile) throws IOException {
        log.info("Saving book with name: {}", book.getName());

        if (bookRepository.existsByNameIgnoreCase(book.getName())) {
            log.warn("A book with the title '{}' already exists", book.getName());
            throw new EntityAlreadyExistsException("A book with the title: " + book.getName() + " already exists.");
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            log.info("Saving image for the book: {}", book.getName());
            book.setImage(imageFile.getBytes());
        }
        bookRepository.save(book);
        if (pdfFile != null && !pdfFile.isEmpty()) {
            // отдельно сохраняем контент
            log.info("Saving PDF content for the book: {}", book.getName());
            bookRepository.updateContent(pdfFile.getBytes(), book.getId());
        }
        log.info("Book with name '{}' saved successfully", book.getName());
        return book;
    }

    @Override
    public List<Book> findTopBooks(int limit) {
        log.info("Getting top {} books", limit);
        return bookRepository.findTopBooks(PageRequest.of(0, limit,
                Sort.by(Sort.Direction.DESC, "view_count")));
    }
    @Override
    public Optional<byte[]> getContent(long id) {
        log.info("Getting content for book with id: {}", id);
        return bookRepository.getContent(id);
    }
    @Override
    @Transactional
    public void updateViewCount(long updatedViewCount, long id) {
        log.info("Updating view count for book with id: {}, new view count: {}", id, updatedViewCount);
        bookRepository.updateViewCount(updatedViewCount, id);
    }
    @Override
    public long getViewCountById(long id) {
        log.info("Getting view count for book with id: {}", id);
        return bookRepository.getViewCountById(id);
    }
    @Override
    @Transactional
    public void updateRating(long book_id, int rating, Principal principal) {

        log.info("Updating rating for book with id: {}, new rating: {}", book_id, rating);
        Book book = bookRepository.findById(book_id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        // Получение текущего аутентифицированного пользователя
        User user = Optional.ofNullable(userRepository.findByUsername(principal.getName()))
                .orElseThrow(() -> new SecurityException("You are not allowed to rate the book."));

        boolean alreadyVoted = voteRepository.existsByUserAndBook(user, book);
        if (alreadyVoted) {
            log.warn("User '{}' has already voted for book '{}'", user.getUsername(), book.getName());
            throw new IllegalStateException("User has already voted for this book");
        }
        voteRepository.save(new Vote(rating, book, user));

        // Новый суммарный рейтинг и количество голосов
        long newTotalRating = bookRepository.getTotalRatingById(book_id) + rating;
        long newVoteCount = bookRepository.getTotalVoteCountById(book_id) + 1;

        double newAvgRating = calcAverageRating(newTotalRating, newVoteCount);
        bookRepository.updateRating(newTotalRating, newVoteCount, newAvgRating, book_id);
        log.info("Rating updated for book with id: {}, new average rating: {}", book_id, newAvgRating);
    }

    public double calcAverageRating(long totalRating, long totalVoteCount) {
        if (totalRating == 0 || totalVoteCount == 0) {
            return 0;
        }
        return (double) totalRating / totalVoteCount;
    }

    @Transactional
    @Override
    @CacheEvict(value = "books", key = "#id")
    public void updateBook(long id, Book updatedBook) {
        log.info("Updating book with id: {}", id);
        Book existingBook = bookRepository.findById(id).orElseThrow(
                     () -> new EntityNotFoundException("Book not found"));

        Optional.ofNullable(updatedBook.getName()).ifPresent(existingBook::setName);
        Optional.ofNullable(updatedBook.getIsbn()).ifPresent(existingBook::setIsbn);
        Optional.ofNullable(updatedBook.getDescr()).ifPresent(existingBook::setDescr);
        bookRepository.save(existingBook);
        log.info("Book with id: {} updated successfully", id);

    }

    @Override
    @Transactional
    @CacheEvict(value = "books", key = "#id")
    public void delete(long id) {
        bookRepository.deleteById(id);
        log.info("Book with id: {} successfully deleted", id);
    }
}
