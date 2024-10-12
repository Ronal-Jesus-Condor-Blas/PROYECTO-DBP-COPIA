package com.proyecto_dbp.user.domain;

import com.proyecto_dbp.user.dto.UserDto;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return mapToDto(user.get());
        }
        return null;
    }

    public UserDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return mapToDto(user.get());
        }
        return null;
    }

    public UserDto createUser(UserDto userDto) {
        User user = mapToEntity(userDto);
        user = userRepository.save(user);
        return mapToDto(user);
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setBio(userDto.getBio());
            user.setUserType(userDto.getUserType());
            user = userRepository.save(user);
            return mapToDto(user);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setBio(user.getBio());
        userDto.setUserType(user.getUserType());
        return userDto;
    }

    private User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setBio(userDto.getBio());
        user.setUserType(userDto.getUserType());
        return user;
    }
}