package com.proyecto_dbp.post.domain;

import com.proyecto_dbp.post.dto.PostRequestDto;
import com.proyecto_dbp.post.dto.PostResponseDto;
import com.proyecto_dbp.post.infrastructure.PostRepository;
import com.proyecto_dbp.user.domain.User;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository; // Add UserRepository

    public PostResponseDto getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(this::mapToDto).orElse(null);
    }

    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = mapToEntity(postRequestDto);
        post.setCreatedDate(LocalDateTime.now()); // Set created date
        post = postRepository.save(post);
        return mapToDto(post);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setContent(postRequestDto.getContent());
            post.setImage(postRequestDto.getImage());
            post.setStatus(postRequestDto.getStatus());
            post = postRepository.save(post);
            return mapToDto(post);
        }
        return null;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private PostResponseDto mapToDto(Post post) {
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(post.getPostId());
        postResponseDto.setContent(post.getContent());
        postResponseDto.setImage(post.getImage());
        postResponseDto.setCreatedDate(post.getCreatedDate());
        postResponseDto.setStatus(post.getStatus());
        postResponseDto.setUserId(post.getUser().getUserId());
        return postResponseDto;
    }

    private Post mapToEntity(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.setContent(postRequestDto.getContent());
        post.setImage(postRequestDto.getImage());
        post.setStatus(postRequestDto.getStatus());

        // Fetch user from UserRepository
        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);

        return post;
    }
}