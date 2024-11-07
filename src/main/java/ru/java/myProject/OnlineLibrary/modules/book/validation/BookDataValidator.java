package ru.java.myProject.OnlineLibrary.modules.book.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.java.myProject.OnlineLibrary.modules.book.entity.Book;
import ru.java.myProject.OnlineLibrary.modules.book.repository.BookRepository;

@Component
public class BookDataValidator implements Validator {

    private final BookRepository bookRepository;

    @Autowired
    public BookDataValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (book.getName() == null || book.getName().trim().isEmpty()) {
            errors.rejectValue("name", "name.empty", "Book name cannot be empty");
        }

        if (bookRepository.existsByNameIgnoreCase(book.getName())) {
            errors.rejectValue("name", "name.duplicate", "Name already exists");
        }
    }
}
