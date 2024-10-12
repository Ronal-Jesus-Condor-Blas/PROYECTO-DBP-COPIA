package com.proyecto_dbp.post.domain;

import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.post.dto.PostRequestDto;
import com.proyecto_dbp.post.dto.PostResponseDto;
import com.proyecto_dbp.post.infrastructure.PostRepository;
import com.proyecto_dbp.user.domain.User;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        return mapToDto(post);
    }

    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        if (postRequestDto.getContent() == null || postRequestDto.getContent().isEmpty()) {
            throw new ValidationException("Content cannot be null or empty");
        }
        Post post = mapToEntity(postRequestDto);
        post.setCreatedDate(LocalDateTime.now());
        post = postRepository.save(post);
        return mapToDto(post);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        if (postRequestDto.getContent() == null || postRequestDto.getContent().isEmpty()) {
            throw new ValidationException("Content cannot be null or empty");
        }
        post.setContent(postRequestDto.getContent());
        post.setImage(postRequestDto.getImage());
        post.setStatus(postRequestDto.getStatus());
        post = postRepository.save(post);
        return mapToDto(post);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id " + id);
        }
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

        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + postRequestDto.getUserId()));
        post.setUser(user);

        return post;
    }
}