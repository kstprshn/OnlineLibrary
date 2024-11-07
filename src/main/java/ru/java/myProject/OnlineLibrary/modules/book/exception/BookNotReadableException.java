package ru.java.myProject.OnlineLibrary.modules.book.exception;

public class BookNotReadableException extends RuntimeException {
    public BookNotReadableException(String message) {
        super(message);
    }
}