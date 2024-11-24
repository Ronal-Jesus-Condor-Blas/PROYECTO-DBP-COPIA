package com.proyecto_dbp.user.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.user.dto.UserRequestDto;
import com.proyecto_dbp.user.dto.UserResponseDto;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.proyecto_dbp.post.infrastructure.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final WebClient webClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private PostRepository  postRepository;

    @Autowired
    public UserService(WebClient webClient) {
        this.webClient = webClient;
    }

    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        return mapToResponseDto(user);
    }

    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
        return mapToResponseDto(user);
    }

    //modificado para enviar email
    public UserResponseDto createUser(UserRequestDto userRequestDto, MultipartFile image) {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new ValidationException("Email already in use");
        }

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = uploadImage(image, "profile");
        }

        User user = mapToEntity(userRequestDto);
        user.setProfilePicture(imageUrl);
        user.setRole(Role.USER);
        user = userRepository.save(user);

        return mapToResponseDto(user);
    }


    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto, MultipartFile image) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        if (userRequestDto.getName() != null) {
            user.setName(userRequestDto.getName());
        }
        if (userRequestDto.getEmail() != null) {
            user.setEmail(userRequestDto.getEmail());
        }
        if (userRequestDto.getBio() != null) {
            user.setBio(userRequestDto.getBio());
        }
        if (userRequestDto.getUserType() != null) {
            user.setUserType(userRequestDto.getUserType());
        }

        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadImage(image, "profile");
            user.setProfilePicture(imageUrl);
        }

        user = userRepository.save(user);
        return mapToResponseDto(user);
    }


    private String uploadImage(MultipartFile image, String type) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("type", type);
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

            return bodyJson.get("image_url").asText();
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image", e);
        }
    }


    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        // Delete related posts
        postRepository.deleteByUserUserId(userId);

        // Clear other relationships if necessary
        user.getComments().clear();
        user.getFollowers().clear();
        user.getLikedPosts().clear();
        user.getFoodRatings().clear();
        user.getRestaurantRatings().clear();

        // Save the user to update the database
        userRepository.save(user);

        // Delete the user
        userRepository.deleteById(userId);
    }

    private UserResponseDto mapToResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(user.getUserId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setBio(user.getBio());
        userResponseDto.setUserType(user.getUserType());
        userResponseDto.setProfilePicture(user.getProfilePicture());
        return userResponseDto;
    }

    private User mapToEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setBio(userRequestDto.getBio());
        user.setUserType(userRequestDto.getUserType());
        user.setPassword(userRequestDto.getPassword());
        return user;
    }
    //Code for patch mapping
    public UserResponseDto updateUserPartial(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        if (userRequestDto.getName() != null) {
            user.setName(userRequestDto.getName());
        }
        if (userRequestDto.getEmail() != null) {
            user.setEmail(userRequestDto.getEmail());
        }
        if (userRequestDto.getBio() != null) {
            user.setBio(userRequestDto.getBio());
        }
        if (userRequestDto.getUserType() != null) {
            user.setUserType(userRequestDto.getUserType());
        }
        user = userRepository.save(user);
        return mapToResponseDto(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return (UserDetails) user;
        };
    }
}