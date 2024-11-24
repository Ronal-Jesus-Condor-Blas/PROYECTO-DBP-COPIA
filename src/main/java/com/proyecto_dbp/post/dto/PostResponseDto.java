package com.proyecto_dbp.post.dto;

import com.proyecto_dbp.post.domain.PostStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDto {
    private Long postId;
    private Long userId;
    private String userName; // Nuevo: Nombre del usuario
    private String userProfilePicture; // Nuevo: URL de la imagen de perfil del usuario
    private String content;
    private String image;
    private LocalDateTime createdDate;
    private PostStatus status;
    private String title;
}