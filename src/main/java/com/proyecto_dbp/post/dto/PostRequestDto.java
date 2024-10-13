package com.proyecto_dbp.post.dto;

import com.proyecto_dbp.post.domain.PostStatus;
import lombok.Data;

@Data
public class PostRequestDto {
    private Long userId;
    private String content;
    private String image;
    private PostStatus status;
    private String title;


    public String getTitle() {
        return title;
    }
}