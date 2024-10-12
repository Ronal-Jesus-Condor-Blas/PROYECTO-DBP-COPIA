package com.proyecto_dbp.user.domain;

import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.user.dto.UserRequestDto;
import com.proyecto_dbp.user.dto.UserResponseDto;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new ValidationException("Email already in use");
        }
        User user = mapToEntity(userRequestDto);
        user = userRepository.save(user);
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

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
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
}