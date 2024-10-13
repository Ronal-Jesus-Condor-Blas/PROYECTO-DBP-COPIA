package com.proyecto_dbp.comment.application;

import com.proyecto_dbp.comment.domain.CommentService;
import com.proyecto_dbp.comment.dto.CommentRequestDto;
import com.proyecto_dbp.comment.dto.CommentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        CommentResponseDto comment = commentService.getCommentById(id);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto createdComment = commentService.createComment(commentRequestDto);
        return ResponseEntity.ok(createdComment);
    }

    //**
    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> parchComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto updatedComment = commentService.updateComment(id, commentRequestDto);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto updatedComment = commentService.updateComment(id, commentRequestDto);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}