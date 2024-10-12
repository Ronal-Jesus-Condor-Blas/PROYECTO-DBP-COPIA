package com.proyecto_dbp.comment.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private Long userId;
    private Long postId;
    private String content;
}