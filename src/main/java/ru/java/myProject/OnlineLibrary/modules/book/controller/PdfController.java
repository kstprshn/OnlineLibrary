package ru.java.myProject.OnlineLibrary.modules.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.java.myProject.OnlineLibrary.modules.book.service.BookServiceImpl;
import ru.java.myProject.OnlineLibrary.modules.book.exception.BookNotReadableException;

import java.util.Optional;


@RestController
@RequestMapping("/api/book")
@Tag(name = "PDF API", description = "Methods of working with PDF files")
public class PdfController {

    private final BookServiceImpl bookServiceImpl;

    @Autowired
    public PdfController(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping("/{id}/pdf")
    @Operation(summary = "Download the PDF file")
    public ResponseEntity<byte[]> getPdfContent(@PathVariable("id") Long id) {

        Optional<byte[]> pdfContent = bookServiceImpl.getContent(id);
        if (pdfContent.isEmpty()) {
            throw new BookNotReadableException("The book is not available for reading");
        }

        long totalViews = bookServiceImpl.getViewCountById(id);
        bookServiceImpl.updateViewCount(totalViews + 1, id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdfContent.get().length);
        headers.setContentDispositionFormData("inline", "book_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent.get());
    }
}
