package com.proyecto_dbp.comment.infrastructure;

import com.proyecto_dbp.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostPostId(Long postId);
}