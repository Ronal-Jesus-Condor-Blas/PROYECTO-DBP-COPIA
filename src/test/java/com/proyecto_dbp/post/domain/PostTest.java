package com.proyecto_dbp.post.domain;

import com.proyecto_dbp.comment.domain.Comment;
import com.proyecto_dbp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {
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
        post.setTitle("Test Title");

        Comment comment1 = new Comment();
        comment1.setCommentId(1L);
        comment1.setContent("First comment");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setCommentId(2L);
        comment2.setContent("Second comment");
        comment2.setPost(post);

        Set<Comment> comments = new HashSet<>();
        comments.add(comment1);
        comments.add(comment2);
        post.setComments(comments);

        User likedUser1 = new User();
        likedUser1.setUserId(2L);
        likedUser1.setName("Liked User 1");

        User likedUser2 = new User();
        likedUser2.setUserId(3L);
        likedUser2.setName("Liked User 2");

        Set<User> likedBy = new HashSet<>();
        likedBy.add(likedUser1);
        likedBy.add(likedUser2);
        post.setLikedBy(likedBy);
    }

    @Test
    public void testPostCreation() {
        assertNotNull(post);
        assertEquals(1L, post.getPostId());
        assertEquals(user, post.getUser());
        assertEquals("This is a test post", post.getContent());
        assertEquals("image.jpg", post.getImage());
        assertEquals(LocalDateTime.now().getDayOfWeek(), post.getCreatedDate().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), post.getCreatedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), post.getCreatedDate().getMonth());
        assertEquals(PostStatus.ACTIVE, post.getStatus());
        assertEquals("Test Title", post.getTitle());
        assertEquals(2, post.getComments().size());
        assertEquals(2, post.getLikedBy().size());
    }

    @Test
    public void testAddComment() {
        Comment comment3 = new Comment();
        comment3.setCommentId(3L);
        comment3.setContent("Third comment");
        comment3.setPost(post);
        Set<Comment> comments = new HashSet<>(post.getComments());
        comments.add(comment3);
        post.setComments(comments);
        assertEquals(3, post.getComments().size());
    }

    @Test
    public void testRemoveComment() {
        Set<Comment> comments = new HashSet<>(post.getComments());
        comments.removeIf(comment -> comment.getCommentId().equals(1L));
        post.setComments(comments);
        assertEquals(1, post.getComments().size());
    }

    @Test
    public void testAddLike() {
        User likedUser3 = new User();
        likedUser3.setUserId(4L);
        likedUser3.setName("Liked User 3");
        Set<User> likedBy = new HashSet<>(post.getLikedBy());
        likedBy.add(likedUser3);
        post.setLikedBy(likedBy);
        assertEquals(3, post.getLikedBy().size());
    }

    @Test
    public void testRemoveLike() {
        Set<User> likedBy = new HashSet<>(post.getLikedBy());
        likedBy.removeIf(user -> user.getUserId().equals(2L));
        post.setLikedBy(likedBy);
        assertEquals(1, post.getLikedBy().size());
    }

    @Test
    public void testUpdatePostContent() {
        post.setContent("Updated post content");
        assertEquals("Updated post content", post.getContent());
    }

    @Test
    public void testUpdatePostTitle() {
        post.setTitle("Updated Title");
        assertEquals("Updated Title", post.getTitle());
    }

    @Test
    public void testUpdatePostStatus() {
        post.setStatus(PostStatus.DELETED);
        assertEquals(PostStatus.DELETED, post.getStatus());
    }
}