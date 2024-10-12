package com.proyecto_dbp.user.domain;

import com.proyecto_dbp.user.dto.UserRequestDto;
import com.proyecto_dbp.user.dto.UserResponseDto;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDto getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(this::mapToResponseDto).orElse(null);
    }

    public UserResponseDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(this::mapToResponseDto).orElse(null);
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = mapToEntity(userRequestDto);
        user = userRepository.save(user);
        return mapToResponseDto(user);
    }

    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userRequestDto.getName());
            user.setEmail(userRequestDto.getEmail());
            user.setBio(userRequestDto.getBio());
            user.setUserType(userRequestDto.getUserType());
            user = userRepository.save(user);
            return mapToResponseDto(user);
        }
        return null;
    }

    public void deleteUser(Long userId) {
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