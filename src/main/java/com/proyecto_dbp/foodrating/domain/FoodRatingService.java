package com.proyecto_dbp.foodrating.domain;

import com.proyecto_dbp.foodrating.infrastructure.FoodRatingRepository;
import com.proyecto_dbp.food.domain.Food;
import com.proyecto_dbp.foodrating.dto.FoodRatingRequestDto;
import com.proyecto_dbp.foodrating.dto.FoodRatingResponseDto;
import com.proyecto_dbp.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodRatingService {

    @Autowired
    private FoodRatingRepository foodRatingRepository;

    public FoodRatingResponseDto getFoodRatingById(Long id) {
        Optional<FoodRating> foodRating = foodRatingRepository.findById(id);
        return foodRating.map(this::mapToDto).orElse(null);
    }

    public List<FoodRatingResponseDto> getFoodRatingsByFoodId(Long foodId) {
        List<FoodRating> foodRatings = foodRatingRepository.findByFoodId(foodId);
        return foodRatings.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public FoodRatingResponseDto createFoodRating(FoodRatingRequestDto foodRatingRequestDto) {
        FoodRating foodRating = mapToEntity(foodRatingRequestDto);
        foodRating = foodRatingRepository.save(foodRating);
        return mapToDto(foodRating);
    }

    public FoodRatingResponseDto updateFoodRating(Long id, FoodRatingRequestDto foodRatingRequestDto) {
        Optional<FoodRating> foodRatingOptional = foodRatingRepository.findById(id);
        if (foodRatingOptional.isPresent()) {
            FoodRating foodRating = foodRatingOptional.get();
            foodRating.setRating(foodRatingRequestDto.getRating());
            foodRating.setComment(foodRatingRequestDto.getComment());
            foodRating = foodRatingRepository.save(foodRating);
            return mapToDto(foodRating);
        }
        return null;
    }

    public void deleteFoodRating(Long id) {
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