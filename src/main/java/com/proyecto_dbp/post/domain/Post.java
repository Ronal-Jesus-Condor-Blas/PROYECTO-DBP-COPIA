package com.proyecto_dbp.post.domain;

import com.proyecto_dbp.comment.domain.Comment;
import com.proyecto_dbp.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Clave foránea hacia User
    private User user;  // Un post es creado por un usuario

    private String image;

    private String content;

    private LocalDateTime createdDate;

    private String title;

    @Enumerated(EnumType.STRING)
    private PostStatus status;  // Estado del post (activo/eliminado)

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments;  // Un post puede tener muchos comentarios

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedBy; // Usuarios que han dado like a este post

    public String getTitle() {
        return title;
    }
}
