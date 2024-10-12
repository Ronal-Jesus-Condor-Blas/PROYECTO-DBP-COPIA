package com.proyecto_dbp.foodrating.domain;

import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.foodrating.infrastructure.FoodRatingRepository;
import com.proyecto_dbp.food.domain.Food;
import com.proyecto_dbp.foodrating.dto.FoodRatingRequestDto;
import com.proyecto_dbp.foodrating.dto.FoodRatingResponseDto;
import com.proyecto_dbp.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodRatingService {

    @Autowired
    private FoodRatingRepository foodRatingRepository;

    public FoodRatingResponseDto getFoodRatingById(Long id) {
        FoodRating foodRating = foodRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodRating not found with id " + id));
        return mapToDto(foodRating);
    }

    public List<FoodRatingResponseDto> getFoodRatingsByFoodId(Long foodId) {
        List<FoodRating> foodRatings = foodRatingRepository.findByFoodId(foodId);
        if (foodRatings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for food with id " + foodId);
        }
        return foodRatings.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public FoodRatingResponseDto createFoodRating(FoodRatingRequestDto foodRatingRequestDto) {
        if (foodRatingRequestDto.getRating() < 1 || foodRatingRequestDto.getRating() > 5) {
            throw new ValidationException("Rating must be between 1 and 5");
        }
        FoodRating foodRating = mapToEntity(foodRatingRequestDto);
        foodRating = foodRatingRepository.save(foodRating);
        return mapToDto(foodRating);
    }

    public FoodRatingResponseDto updateFoodRating(Long id, FoodRatingRequestDto foodRatingRequestDto) {
        FoodRating foodRating = foodRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodRating not found with id " + id));
        if (foodRatingRequestDto.getRating() < 1 || foodRatingRequestDto.getRating() > 5) {
            throw new ValidationException("Rating must be between 1 and 5");
        }
        foodRating.setRating(foodRatingRequestDto.getRating());
        foodRating.setComment(foodRatingRequestDto.getComment());
        foodRating = foodRatingRepository.save(foodRating);
        return mapToDto(foodRating);
    }

    public void deleteFoodRating(Long id) {
        if (!foodRatingRepository.existsById(id)) {
            throw new ResourceNotFoundException("FoodRating not found with id " + id);
        }
        foodRatingRepository.deleteById(id);
    }

    private FoodRatingResponseDto mapToDto(FoodRating foodRating) {
        FoodRatingResponseDto foodRatingResponseDto = new FoodRatingResponseDto();
        foodRatingResponseDto.setFoodRatingId(foodRating.getFoodRatingId());
        foodRatingResponseDto.setRating(foodRating.getRating());
        foodRatingResponseDto.setComment(foodRating.getComment());
        foodRatingResponseDto.setRatingDate(foodRating.getRatingDate());
        foodRatingResponseDto.setFoodId(foodRating.getFood().getFoodId());
        foodRatingResponseDto.setUserId(foodRating.getUser().getUserId());
        return foodRatingResponseDto;
    }

    private FoodRating mapToEntity(FoodRatingRequestDto foodRatingRequestDto) {
        FoodRating foodRating = new FoodRating();
        foodRating.setRating(foodRatingRequestDto.getRating());
        foodRating.setComment(foodRatingRequestDto.getComment());
        // Aquí deberías obtener el usuario y el plato desde sus respectivos servicios
        User user = new User();
        user.setUserId(foodRatingRequestDto.getUserId());
        foodRating.setUser(user);
        Food food = new Food();
        food.setFoodId(foodRatingRequestDto.getFoodId());
        foodRating.setFood(food);
        return foodRating;
    }
}