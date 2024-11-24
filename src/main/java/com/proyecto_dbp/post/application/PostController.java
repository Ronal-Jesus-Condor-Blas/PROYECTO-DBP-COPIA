package com.proyecto_dbp.post.application;

import com.proyecto_dbp.post.domain.PostService;
import com.proyecto_dbp.post.dto.PostDTO;
import com.proyecto_dbp.post.dto.PostRequestDto;
import com.proyecto_dbp.post.dto.PostResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<PostResponseDto> createPost(
            @RequestPart("post") PostRequestDto postRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        PostResponseDto createdPost = postService.createPost(postRequestDto, image);
        return ResponseEntity.ok(createdPost);
    }

    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<PostResponseDto> parchPost (
            @PathVariable Long id,
            @RequestPart("post") PostRequestDto postRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        PostResponseDto updatedPost = postService.updatePost(id, postRequestDto, image);
        return ResponseEntity.ok(updatedPost);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @RequestPart("post") PostRequestDto postRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        PostResponseDto updatedPost = postService.updatePost(id, postRequestDto, image);
        System.out.println(updatedPost);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    //MÉTODO O PETICIONES CRUZADAS
    @GetMapping("/users/{userId}/posts")
    public List<PostDTO> getPostsByUserId(@PathVariable Long userId) {
        return postService.getPostsByUserId(userId);
    }

}