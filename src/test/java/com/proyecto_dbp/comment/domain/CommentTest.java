package com.proyecto_dbp.comment.domain;

import com.proyecto_dbp.post.domain.Post;
import com.proyecto_dbp.post.domain.PostStatus;
import com.proyecto_dbp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {
    private Comment comment;
    private Post post;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setName("User 1");
        user.setEmail("user1@example.com");
        user.setPassword("password1");

        post = new Post();
        post.setPostId(1L);
        post.setUser(user);
        post.setContent("This is a test post");
        post.setImage("image.jpg");
        post.setCreatedDate(LocalDateTime.now());
        post.setStatus(PostStatus.ACTIVE);

        comment = new Comment();
        comment.setCommentId(1L);
        comment.setContent("This is a test comment");
        comment.setPost(post);
        comment.setUser(user);
        comment.setCommentDate(LocalDateTime.now());
    }

    @Test
    public void testCommentCreation() {
        assertNotNull(comment);
        assertEquals(1L, comment.getCommentId());
        assertEquals("This is a test comment", comment.getContent());
        assertEquals(post, comment.getPost());
        assertEquals(user, comment.getUser());
        assertEquals(LocalDateTime.now().getDayOfWeek(), comment.getCommentDate().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), comment.getCommentDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), comment.getCommentDate().getMonth());
    }

    @Test
    public void testUpdateCommentContent() {
        comment.setContent("Updated comment content");
        assertEquals("Updated comment content", comment.getContent());
    }

    @Test
    public void testCommentUserRelationship() {
        assertNotNull(comment.getUser());
        assertEquals(user.getUserId(), comment.getUser().getUserId());
    }

    @Test
    public void testCommentPostRelationship() {
        assertNotNull(comment.getPost());
        assertEquals(post.getPostId(), comment.getPost().getPostId());
    }
}