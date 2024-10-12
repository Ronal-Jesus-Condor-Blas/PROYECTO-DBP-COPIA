package com.proyecto_dbp.restaurantrating.domain;

import com.proyecto_dbp.restaurant.domain.Restaurant;
import com.proyecto_dbp.restaurantrating.dto.RestaurantRatingRequestDto;
import com.proyecto_dbp.restaurantrating.dto.RestaurantRatingResponseDto;
import com.proyecto_dbp.restaurantrating.infrastructure.RestaurantRatingRepository;
import com.proyecto_dbp.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantRatingService {

    @Autowired
    private RestaurantRatingRepository restaurantRatingRepository;

    public RestaurantRatingResponseDto getRestaurantRatingById(Long id) {
        Optional<RestaurantRating> restaurantRating = restaurantRatingRepository.findById(id);
        return restaurantRating.map(this::mapToDto).orElse(null);
    }

    public List<RestaurantRatingResponseDto> getRestaurantRatingsByRestaurantId(Long restaurantId) {
        List<RestaurantRating> restaurantRatings = restaurantRatingRepository.findByRestaurantId(restaurantId);
        return restaurantRatings.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public RestaurantRatingResponseDto createRestaurantRating(RestaurantRatingRequestDto restaurantRatingRequestDto) {
        RestaurantRating restaurantRating = mapToEntity(restaurantRatingRequestDto);
        restaurantRating = restaurantRatingRepository.save(restaurantRating);
        return mapToDto(restaurantRating);
    }

    public RestaurantRatingResponseDto updateRestaurantRating(Long id, RestaurantRatingRequestDto restaurantRatingRequestDto) {
        Optional<RestaurantRating> restaurantRatingOptional = restaurantRatingRepository.findById(id);
        if (restaurantRatingOptional.isPresent()) {
            RestaurantRating restaurantRating = restaurantRatingOptional.get();
            restaurantRating.setRating(restaurantRatingRequestDto.getRating());
            restaurantRating.setComment(restaurantRatingRequestDto.getComment());
            restaurantRating = restaurantRatingRepository.save(restaurantRating);
            return mapToDto(restaurantRating);
        }
        return null;
    }

    public void deleteRestaurantRating(Long id) {
        restaurantRatingRepository.deleteById(id);
    }

    private RestaurantRatingResponseDto mapToDto(RestaurantRating restaurantRating) {
        RestaurantRatingResponseDto restaurantRatingResponseDto = new RestaurantRatingResponseDto();
        restaurantRatingResponseDto.setRestaurantRatingId(restaurantRating.getRestaurantRatingId());
        restaurantRatingResponseDto.setRating(restaurantRating.getRating());
        restaurantRatingResponseDto.setComment(restaurantRating.getComment());
        restaurantRatingResponseDto.setRatingDate(restaurantRating.getRatingDate());
        restaurantRatingResponseDto.setRestaurantId(restaurantRating.getRestaurant().getRestaurantId());  // Asegúrate de que esta línea esté presente
        restaurantRatingResponseDto.setUserId(restaurantRating.getUser().getUserId());
        return restaurantRatingResponseDto;
    }

    private RestaurantRating mapToEntity(RestaurantRatingRequestDto restaurantRatingRequestDto) {
        RestaurantRating restaurantRating = new RestaurantRating();
        restaurantRating.setRating(restaurantRatingRequestDto.getRating());
        restaurantRating.setComment(restaurantRatingRequestDto.getComment());
        // Aquí deberías obtener el usuario y el restaurante desde sus respectivos servicios
        User user = new User();
        user.setUserId(restaurantRatingRequestDto.getUserId());
        restaurantRating.setUser(user);
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantRatingRequestDto.getRestaurantId());
        restaurantRating.setRestaurant(restaurant);
        return restaurantRating;
    }
}