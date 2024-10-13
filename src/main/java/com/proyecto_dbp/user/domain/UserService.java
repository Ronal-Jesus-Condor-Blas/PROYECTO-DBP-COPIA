package com.proyecto_dbp.user.domain;

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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private PostRepository  postRepository;

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
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new ValidationException("Email already in use");
        }
        User user = mapToEntity(userRequestDto);
        user = userRepository.save(user);

        //UserResponseDto userResponseDto = mapToResponseDto(user); //se agrego para la siguiente linea
        //eventPublisher.publishEvent(new UserRegisteredEvent(userResponseDto)); //era user y no dto
        return mapToResponseDto(user);
    }

    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setBio(userRequestDto.getBio());
        user.setUserType(userRequestDto.getUserType());
        user = userRepository.save(user);
        return mapToResponseDto(user);
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