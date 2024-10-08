package com.proyecto_dbp.user.domain;

import com.proyecto_dbp.comment.domain.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    @Size(max = 100)
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    private String profilePicture;
    private String bio;
    @Enumerated(EnumType.STRING)
    private UserType userType; // Tipo de usuario (consumidor/influencer)
    private LocalDateTime registrationDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Post> posts;  // Un usuario puede crear muchos posts
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments;  // Un usuario puede hacer muchos comentarios
    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<User> followers;  // Relación de seguidores y seguidos
    @ManyToMany(mappedBy = "likedBy")
    private Set<Post> likedPosts; // Aquí añadimos los posts que el usuario ha dado like
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<FoodRating> foodRatings; // Un usuario puede calificar muchos platos
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<RestaurantRating> restaurantRatings; // Un usuario puede calificar muchos restaurantes
}
