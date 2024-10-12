package com.proyecto_dbp.user.domain;

import com.proyecto_dbp.comment.domain.Comment;
import com.proyecto_dbp.foodrating.domain.FoodRating;
import com.proyecto_dbp.post.domain.Post;
import com.proyecto_dbp.restaurantrating.domain.RestaurantRating;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    private String name;
    @NotNull
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    @Size(min = 8, max = 64)
    private String password;
    private String bio;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private LocalDateTime registrationDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Post> posts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments;
    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<User> followers;
    @ManyToMany(mappedBy = "likedBy")
    private Set<Post> likedPosts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<FoodRating> foodRatings;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<RestaurantRating> restaurantRatings;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("UserType"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}