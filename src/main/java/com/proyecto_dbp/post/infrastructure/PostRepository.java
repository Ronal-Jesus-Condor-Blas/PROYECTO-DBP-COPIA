package com.proyecto_dbp.post.infrastructure;

import com.proyecto_dbp.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserUserId(Long userId);
    void deleteByUserUserId(Long userId);
}