package com.proyecto_dbp.post.application;

import com.proyecto_dbp.post.domain.PostService;
import com.proyecto_dbp.post.dto.PostRequestDto;
import com.proyecto_dbp.post.dto.PostResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto createdPost = postService.createPost(postRequestDto);
        return ResponseEntity.ok(createdPost);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> parchPost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto updatedPost = postService.updatePost(id, postRequestDto);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto updatedPost = postService.updatePost(id, postRequestDto);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}