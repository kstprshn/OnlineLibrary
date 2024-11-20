package ru.java.myProject.OnlineLibrary.modules.comment.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.java.myProject.OnlineLibrary.modules.comment.dto.CommentCreateDto;
import ru.java.myProject.OnlineLibrary.modules.comment.dto.CommentDto;
import ru.java.myProject.OnlineLibrary.modules.comment.dto.CommentEditDto;
import ru.java.myProject.OnlineLibrary.modules.comment.dto.mapper.CommentMapper;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.Comment;
import ru.java.myProject.OnlineLibrary.modules.comment.entity.CommentParams;
import ru.java.myProject.OnlineLibrary.modules.comment.service.CommentServiceImpl;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comment API", description = "Methods of comment management")
public class CommentController {

    private final CommentServiceImpl commentServiceImpl;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentController(CommentServiceImpl commentServiceImpl, CommentMapper commentMapper) {
        this.commentServiceImpl = commentServiceImpl;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/getComment/{commentId}")
    @Operation(summary = "Get comment by ID")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("commentId") Long id) {
        Comment commentById = commentServiceImpl.findOneByCommentId(id);
        return new ResponseEntity<>(commentMapper.convert(commentById), HttpStatus.OK);
    }

    @GetMapping("/getByUser/{userId}")
    @Operation(summary = "Get comment by user ID")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable("userId") Long userId) {
        List<CommentDto> commentsDto = commentServiceImpl.findByUserId(userId).stream()
                .map(commentMapper::convert).toList();
        return ResponseEntity.ok(commentsDto);
    }

    @GetMapping("/getByBook/{bookId}")
    @Operation(summary = "Get comment by book ID")
    public ResponseEntity<Page<CommentDto>> getCommentsByBookId(@PathVariable("bookId") Long bookId,
                                                                @RequestParam(defaultValue = "0") int page) {
        Page<CommentDto> commentsPage = commentServiceImpl.findByBookId(bookId, page, 15).map(commentMapper::convert);
        return ResponseEntity.ok(commentsPage);
    }

    @PostMapping("/create")
    @Operation(summary = "Add the comment")
    public ResponseEntity<CommentParams> addComment(@RequestBody @Valid CommentCreateDto commentCreateDto,
                                                    Principal principal) throws AccessDeniedException {
            CommentParams commentParams = commentMapper.convertToParams(commentCreateDto);
            Comment savedComment = commentServiceImpl.save(commentParams, principal);
            return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.convertToParams(savedComment));
    }

    @PutMapping("/update/{commentId}")
    @Operation(summary = "Update the comment")
    public ResponseEntity<CommentDto> editComment(@PathVariable("commentId") Long commentId,
                                                  @RequestBody @Valid CommentEditDto commentEditDto,
                                                  Principal principal) throws AccessDeniedException {
            Comment updatedComment = commentServiceImpl
                    .editComment(commentId, commentEditDto.getNewText(), principal);
            return ResponseEntity.ok(commentMapper.convert(updatedComment));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete the comment")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") Long id) {
        commentServiceImpl.remove(id);
        return ResponseEntity.noContent().build();
    }
}
