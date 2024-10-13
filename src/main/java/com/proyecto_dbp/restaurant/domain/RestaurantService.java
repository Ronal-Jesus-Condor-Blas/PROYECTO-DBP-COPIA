package com.proyecto_dbp.restaurant.domain;

import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.restaurant.dto.RestaurantRequestDto;
import com.proyecto_dbp.restaurant.dto.RestaurantResponseDto;
import com.proyecto_dbp.restaurant.infrastructure.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantResponseDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));
        return mapToDto(restaurant);
    }

    public List<RestaurantResponseDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto) {
        if (restaurantRepository.findByEmail(restaurantRequestDto.getEmail()).isPresent()) {
            throw new ValidationException("Email already in use");
        }

        if (restaurantRequestDto.getName() == null || restaurantRequestDto.getName().isEmpty()) {
            throw new ValidationException("Name cannot be null or empty");
        }
        Restaurant restaurant = mapToEntity(restaurantRequestDto);
        restaurant = restaurantRepository.save(restaurant);
        return mapToDto(restaurant);
    }

    public RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));
        if (restaurantRequestDto.getName() == null || restaurantRequestDto.getName().isEmpty()) {
            throw new ValidationException("Name cannot be null or empty");
        }
        restaurant.setName(restaurantRequestDto.getName());
        restaurant.setLocation(restaurantRequestDto.getLocation());
        restaurant.setStatus(restaurantRequestDto.getStatus());
        restaurant = restaurantRepository.save(restaurant);
        return mapToDto(restaurant);
    }

    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant not found with id " + id);
        }
        restaurantRepository.deleteById(id);
    }

    private RestaurantResponseDto mapToDto(Restaurant restaurant) {
        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();
        restaurantResponseDto.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponseDto.setName(restaurant.getName());
        restaurantResponseDto.setLocation(restaurant.getLocation());
        restaurantResponseDto.setStatus(restaurant.getStatus());
        //restaurantResponseDto.setAverageRating(restaurant.getAverageRating());
        restaurantResponseDto.setCreatedDate(restaurant.getCreatedDate());
        restaurantResponseDto.setEmail(restaurant.getEmail());
        return restaurantResponseDto;
    }

    private Restaurant mapToEntity(RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequestDto.getName());
        restaurant.setLocation(restaurantRequestDto.getLocation());
        restaurant.setStatus(restaurantRequestDto.getStatus());
        restaurant.setCreatedDate(LocalDateTime.now());
        restaurant.setEmail(restaurantRequestDto.getEmail());
        return restaurant;
    }

    //parch
    public RestaurantResponseDto parchRestaurant(Long id, RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));

        if (restaurantRequestDto.getName() != null && !restaurantRequestDto.getName().isEmpty()) {
            restaurant.setName(restaurantRequestDto.getName());
        } else {
            restaurant.setName(restaurant.getName());
        }

        if (restaurantRequestDto.getLocation() != null && !restaurantRequestDto.getLocation().isEmpty()) {
            restaurant.setLocation(restaurantRequestDto.getLocation());
        } else {
            restaurant.setLocation(restaurant.getLocation());
        }

        if (restaurantRequestDto.getStatus() != null) {
            restaurant.setStatus(restaurantRequestDto.getStatus());
        } else {
            restaurant.setStatus(restaurant.getStatus());
        }

        restaurant = restaurantRepository.save(restaurant);
        return mapToDto(restaurant);
    }

}