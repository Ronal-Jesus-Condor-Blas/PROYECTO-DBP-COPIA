package com.proyecto_dbp.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long commentId;
    private Long userId;
    private Long postId;
    private String content;
    private LocalDateTime commentDate;
}