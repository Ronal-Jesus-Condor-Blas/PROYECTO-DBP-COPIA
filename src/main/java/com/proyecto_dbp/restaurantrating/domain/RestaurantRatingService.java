package com.proyecto_dbp.restaurantrating.domain;

import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.restaurant.domain.Restaurant;
import com.proyecto_dbp.restaurantrating.dto.RestaurantRatingDTO;
import com.proyecto_dbp.restaurantrating.dto.RestaurantRatingRequestDto;
import com.proyecto_dbp.restaurantrating.dto.RestaurantRatingResponseDto;
import com.proyecto_dbp.restaurantrating.infrastructure.RestaurantRatingRepository;
import com.proyecto_dbp.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantRatingService {

    @Autowired
    private RestaurantRatingRepository restaurantRatingRepository;

    public RestaurantRatingResponseDto getRestaurantRatingById(Long id) {
        RestaurantRating restaurantRating = restaurantRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RestaurantRating no encontrado con id " + id));
        return mapToDto(restaurantRating);
    }

    public List<RestaurantRatingResponseDto> getRestaurantRatingsByRestaurantId(Long restaurantId) {
        List<RestaurantRating> restaurantRatings = restaurantRatingRepository.findByRestaurantRestaurantId(restaurantId);
        if (restaurantRatings.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron calificaciones para el restaurante con id " + restaurantId);
        }
        return restaurantRatings.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public RestaurantRatingResponseDto createRestaurantRating(RestaurantRatingRequestDto restaurantRatingRequestDto) {
        if (restaurantRatingRequestDto.getRating() < 1 || restaurantRatingRequestDto.getRating() > 5) {
            throw new ValidationException("La calificación debe estar entre 1 y 5");
        }
        RestaurantRating restaurantRating = mapToEntity(restaurantRatingRequestDto);
        restaurantRating = restaurantRatingRepository.save(restaurantRating);
        return mapToDto(restaurantRating);
    }

    public RestaurantRatingResponseDto updateRestaurantRating(Long id, RestaurantRatingRequestDto restaurantRatingRequestDto) {
        RestaurantRating restaurantRating = restaurantRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RestaurantRating no encontrado con id " + id));
        restaurantRating.setRating(restaurantRatingRequestDto.getRating());
        restaurantRating.setComment(restaurantRatingRequestDto.getComment());
        restaurantRating = restaurantRatingRepository.save(restaurantRating);
        return mapToDto(restaurantRating);
    }

    public void deleteRestaurantRating(Long id) {
        if (!restaurantRatingRepository.existsById(id)) {
            throw new ResourceNotFoundException("RestaurantRating no encontrado con id " + id);
        }
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
        //
        restaurantRating.setRatingDate(LocalDateTime.now());
        return restaurantRating;
    }

    public RestaurantRatingResponseDto parchRestaurantRating(Long id, RestaurantRatingRequestDto restaurantRatingRequestDto) {
        RestaurantRating restaurantRating = restaurantRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RestaurantRating no encontrado con id " + id));

        if (restaurantRatingRequestDto.getRating() != null) {
            restaurantRating.setRating(restaurantRatingRequestDto.getRating());
        }

        if (restaurantRatingRequestDto.getComment() != null) {
            restaurantRating.setComment(restaurantRatingRequestDto.getComment());
        }

        restaurantRating = restaurantRatingRepository.save(restaurantRating);
        return mapToDto(restaurantRating);
    }

    //MÉTODOS O PETICIONES CRUZADAS
    public List<RestaurantRatingDTO> getRestaurantRatingsByUserId(Long userId) {
        List<RestaurantRating> restaurantRatings = restaurantRatingRepository.findByUserUserId(userId);
        return restaurantRatings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<RestaurantRatingDTO> getCommentsByRestaurantId(Long restaurantId) {
        List<RestaurantRating> comments = restaurantRatingRepository.findByRestaurantRestaurantId(restaurantId);
        return comments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private RestaurantRatingDTO convertToDTO(RestaurantRating restaurantRating) {
        RestaurantRatingDTO restaurantRatingDTO = new RestaurantRatingDTO();
        restaurantRatingDTO.setRatingId(restaurantRating.getRatingId());
        restaurantRatingDTO.setRating(restaurantRating.getRating());
        restaurantRatingDTO.setComment(restaurantRating.getComment());
        restaurantRatingDTO.setRestaurantId(restaurantRating.getRestaurant().getRestaurantId());
        restaurantRatingDTO.setUserId(restaurantRating.getUser().getUserId());
        return restaurantRatingDTO;
    }
}