// PostDTO.java
package com.proyecto_dbp.post.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Long postId;
    private String title;
    private String content;
}