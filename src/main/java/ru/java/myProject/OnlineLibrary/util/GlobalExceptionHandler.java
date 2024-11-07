package ru.java.myProject.OnlineLibrary.util;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import ru.java.myProject.OnlineLibrary.modules.admin.exception.UserNotFoundException;
import ru.java.myProject.OnlineLibrary.modules.book.exception.BookNotReadableException;
import ru.java.myProject.OnlineLibrary.modules.user.exception.EmailAlreadyExistsException;
import ru.java.myProject.OnlineLibrary.modules.user.exception.UsernameAlreadyExistsException;

import javax.security.auth.login.CredentialException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UsernameAlreadyExistsException.class,
            EmailAlreadyExistsException.class,
            EntityAlreadyExistsException.class
    })
    public ResponseEntity<String> handleConflictExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            EntityNotFoundException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<String> handleNotFoundExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            SecurityException.class
    })
    public ResponseEntity<String> handleForbiddenExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials: " + ex.getMessage());
    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<String> handleCredentialException(CredentialException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials: " + ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointer(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<String> handleMessagingException(MessagingException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email sending failed: " + ex.getMessage());
    }

    @ExceptionHandler(BookNotReadableException.class)
    public ResponseEntity<String> handleBookNotReadableException(BookNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException(MultipartException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload error: " + ex.getMessage());
    }
}
