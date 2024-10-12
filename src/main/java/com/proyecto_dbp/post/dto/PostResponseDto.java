package com.proyecto_dbp.post.dto;

import com.proyecto_dbp.post.domain.PostStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDto {
    private Long postId;
    private Long userId;
    private String content;
    private String image;
    private LocalDateTime createdDate;
    private PostStatus status;
}