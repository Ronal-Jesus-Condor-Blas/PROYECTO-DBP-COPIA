package com.proyecto_dbp.user.domain;

import com.proyecto_dbp.comment.domain.Comment;
import com.proyecto_dbp.post.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("User 1");
        user.setEmail("user1@example.com");
        user.setPassword("password1");
        user.setBio("This is a bio");
        user.setUserType(UserType.INFLUENCER);
        user.setRegistrationDate(LocalDateTime.now());

        User follower1 = new User();
        follower1.setName("Follower 1");
        follower1.setEmail("follower1@example.com");
        follower1.setPassword("password2");
        follower1.setBio("This is a bio");
        follower1.setUserType(UserType.CONSUMER);
        follower1.setRegistrationDate(LocalDateTime.now());

        User follower2 = new User();
        follower2.setName("Follower 2");
        follower2.setEmail("follower2@example.com");
        follower2.setPassword("password3");
        follower2.setBio("This is a bio");
        follower2.setUserType(UserType.CONSUMER);
        follower2.setRegistrationDate(LocalDateTime.now());

        Set<User> followers = new HashSet<>();
        followers.add(follower1);
        followers.add(follower2);
        user.setFollowers(followers);
    }

    @Test
    public void testUserCreation() {
        assertNotNull(user);
        assertEquals("User 1", user.getName());
        assertEquals("user1@example.com", user.getEmail());
        assertEquals("password1", user.getPassword());
        assertEquals("This is a bio", user.getBio());
        assertEquals(UserType.INFLUENCER, user.getUserType());
        assertEquals(LocalDateTime.now().getDayOfWeek(), user.getRegistrationDate().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), user.getRegistrationDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), user.getRegistrationDate().getMonth());
        assertEquals(2, user.getFollowers().size());
    }

    @Test
    public void testAddFollower() {
        User follower3 = new User();
        follower3.setName("Follower 3");
        follower3.setEmail("follower3@example.com");
        follower3.setPassword("password4");
        follower3.setBio("This is a bio");
        follower3.setUserType(UserType.CONSUMER);
        follower3.setRegistrationDate(LocalDateTime.now());
        Set<User> followers = new HashSet<>(user.getFollowers());
        followers.add(follower3);
        user.setFollowers(followers);
        assertEquals(3, user.getFollowers().size());
    }

    @Test
    public void testRemoveFollower() {
        Set<User> followers = new HashSet<>(user.getFollowers());
        followers.remove(followers.iterator().next());
        user.setFollowers(followers);
        assertEquals(1, user.getFollowers().size());
    }

    @Test
    public void testUserPosts() {
        Post post = new Post();
        post.setTitle("Post Title");
        post.setContent("Post Content");
        post.setUser(user);

        Set<Post> posts = new HashSet<>();
        posts.add(post);
        user.setPosts(posts);

        assertEquals(1, user.getPosts().size());
        assertEquals("Post Title", user.getPosts().iterator().next().getTitle());
    }

    @Test
    public void testUserComments() {
        Comment comment = new Comment();
        comment.setContent("Comment Content");
        comment.setUser(user);

        Set<Comment> comments = new HashSet<>();
        comments.add(comment);
        user.setComments(comments);

        assertEquals(1, user.getComments().size());
        assertEquals("Comment Content", user.getComments().iterator().next().getContent());
    }

    @Test
    public void testUserLikedPosts() {
        Post likedPost = new Post();
        likedPost.setTitle("Liked Post Title");
        likedPost.setContent("Liked Post Content");

        Set<Post> likedPosts = new HashSet<>();
        likedPosts.add(likedPost);
        user.setLikedPosts(likedPosts);

        assertEquals(1, user.getLikedPosts().size());
        assertEquals("Liked Post Title", user.getLikedPosts().iterator().next().getTitle());
    }
}