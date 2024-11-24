package com.proyecto_dbp.post.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto_dbp.comment.domain.Comment;
import com.proyecto_dbp.comment.dto.CommentResponseDto;
import com.proyecto_dbp.comment.infrastructure.CommentRepository;
import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.post.dto.PostDTO;
import com.proyecto_dbp.post.dto.PostRequestDto;
import com.proyecto_dbp.post.dto.PostResponseDto;
import com.proyecto_dbp.post.infrastructure.PostRepository;
import com.proyecto_dbp.user.domain.User;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final WebClient webClient;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public PostService(WebClient webClient, PostRepository postRepository) {
        this.webClient = webClient;
        this.postRepository = postRepository;
    }

    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        return mapToDto(post);
    }

    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto, MultipartFile image) {
        if (postRequestDto.getContent() == null || postRequestDto.getContent().isEmpty()) {
            throw new ValidationException("Content cannot be null or empty");
        }

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            try {
                // Codificar la imagen en base64
                String base64Image = Base64.getEncoder().encodeToString(image.getBytes());

                // Crear el cuerpo de la solicitud
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("type", "post");
                requestBody.put("image", base64Image);

                // Enviar la solicitud POST
                String response = webClient.post()
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                // Parsear la respuesta para obtener la URL de la imagen
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(response);

                // Obtener el campo 'body' como texto
                String bodyContent = responseJson.get("body").asText();

                // Parsear el JSON anidado en 'body'
                JsonNode bodyJson = objectMapper.readTree(bodyContent);

                // Acceder al campo 'image_url'
                imageUrl = bodyJson.get("image_url").asText();

            } catch (IOException e) {
                throw new RuntimeException("Error processing the image", e);
            }
        }

        Post post = mapToEntity(postRequestDto);
        post.setCreatedDate(LocalDateTime.now());
        post.setImage(imageUrl);
        post = postRepository.save(post);

        return mapToDto(post);
    }

    //patch
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, MultipartFile image) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));

        // Actualizar campos básicos del Post
        if (postRequestDto.getContent() != null) {
            post.setContent(postRequestDto.getContent());
        }

        if (postRequestDto.getTitle() != null) {
            post.setTitle(postRequestDto.getTitle());
        }

        if (postRequestDto.getStatus() != null) {
            post.setStatus(postRequestDto.getStatus());
        }

        // Procesar la nueva imagen (si se proporciona)
        if (image != null && !image.isEmpty()) {
            try {
                String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("type", "post");
                requestBody.put("image", base64Image);

                String response = webClient.post()
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(response);
                String bodyContent = responseJson.get("body").asText();
                JsonNode bodyJson = objectMapper.readTree(bodyContent);
                String imageUrl = bodyJson.get("image_url").asText();

                post.setImage(imageUrl); // Actualizar la URL de la imagen
            } catch (IOException e) {
                throw new RuntimeException("Error processing the image", e);
            }
        }

        post = postRepository.save(post);
        return mapToDto(post);
    }


    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));

        List<Comment> comments = commentRepository.findByPostPostId(id);

        commentRepository.deleteAll();

        postRepository.delete(post);
    }

    private PostResponseDto mapToDto(Post post) {
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setPostId(post.getPostId());
        postResponseDto.setContent(post.getContent());
        postResponseDto.setImage(post.getImage());
        postResponseDto.setCreatedDate(post.getCreatedDate());
        postResponseDto.setStatus(post.getStatus());
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setUserId(post.getUser().getUserId());
        postResponseDto.setUserName(post.getUser().getName()); // Obtener el nombre del usuario
        postResponseDto.setUserProfilePicture(post.getUser().getProfilePicture()); // Obtener la imagen de perfil del usuario
        return postResponseDto;
    }


    private Post mapToEntity(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.setContent(postRequestDto.getContent());
        post.setImage(postRequestDto.getImage());
        post.setStatus(postRequestDto.getStatus());
        post.setTitle(postRequestDto.getTitle());

        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + postRequestDto.getUserId()));
        post.setUser(user);

        return post;
    }

    //MÉTODO O PETICIONES CRUZADAS
    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findByUserUserId(userId);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(post.getPostId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        return postDTO;
    }
}