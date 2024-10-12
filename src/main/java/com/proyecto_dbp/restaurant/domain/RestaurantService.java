package com.proyecto_dbp.restaurant.domain;

import com.proyecto_dbp.restaurant.dto.RestaurantRequestDto;
import com.proyecto_dbp.restaurant.dto.RestaurantResponseDto;
import com.proyecto_dbp.restaurant.infrastructure.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantResponseDto getRestaurantById(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return restaurant.map(this::mapToDto).orElse(null);
    }

    public List<RestaurantResponseDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = mapToEntity(restaurantRequestDto);
        restaurant = restaurantRepository.save(restaurant);
        return mapToDto(restaurant);
    }

    public RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto restaurantRequestDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            restaurant.setName(restaurantRequestDto.getName());
            restaurant.setLocation(restaurantRequestDto.getLocation());
            restaurant.setStatus(restaurantRequestDto.getStatus());
            restaurant = restaurantRepository.save(restaurant);
            return mapToDto(restaurant);
        }
        return null;
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    private RestaurantResponseDto mapToDto(Restaurant restaurant) {
        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();
        restaurantResponseDto.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponseDto.setName(restaurant.getName());
        restaurantResponseDto.setLocation(restaurant.getLocation());
        restaurantResponseDto.setStatus(restaurant.getStatus());
        restaurantResponseDto.setAverageRating(restaurant.getAverageRating());
        restaurantResponseDto.setCreatedDate(restaurant.getCreatedDate());
        return restaurantResponseDto;
    }

    private Restaurant mapToEntity(RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequestDto.getName());
        restaurant.setLocation(restaurantRequestDto.getLocation());
        restaurant.setStatus(restaurantRequestDto.getStatus());
        return restaurant;
    }
}