package com.proyecto_dbp.foodrating.domain;

import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.food.domain.Food;
import com.proyecto_dbp.food.infrastructure.FoodRepository;
import com.proyecto_dbp.foodrating.dto.FoodRatingDTO;
import com.proyecto_dbp.foodrating.dto.FoodRatingRequestDto;
import com.proyecto_dbp.foodrating.dto.FoodRatingResponseDto;
import com.proyecto_dbp.foodrating.infrastructure.FoodRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodRatingService {

    @Autowired
    private FoodRatingRepository foodRatingRepository;

    @Autowired
    private FoodRepository foodRepository;

    public FoodRatingResponseDto createFoodRating(FoodRatingRequestDto foodRatingRequestDto) {
        Food food = foodRepository.findById(foodRatingRequestDto.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id " + foodRatingRequestDto.getFoodId()));
        //**
        if (foodRatingRequestDto.getRating() < 1 || foodRatingRequestDto.getRating() > 5) {
            throw new ValidationException("La calificación debe estar entre 1 y 5");
        }
        //**
        FoodRating foodRating = mapToEntity(foodRatingRequestDto);
        foodRating.setFood(food);  // Set the food entity
        foodRating = foodRatingRepository.save(foodRating);
        return mapToDto(foodRating);
    }

    private FoodRatingResponseDto mapToDto(FoodRating foodRating) {
        FoodRatingResponseDto foodRatingResponseDto = new FoodRatingResponseDto();
        foodRatingResponseDto.setFoodRatingId(foodRating.getFoodRatingId());
        foodRatingResponseDto.setFoodId(foodRating.getFood().getFoodId());
        foodRatingResponseDto.setUserId(foodRating.getUserId());
        foodRatingResponseDto.setRating(foodRating.getRating());
        foodRatingResponseDto.setComment(foodRating.getComment());
        foodRatingResponseDto.setRatingDate(foodRating.getRatingDate());
        return foodRatingResponseDto;
    }

    private FoodRating mapToEntity(FoodRatingRequestDto foodRatingRequestDto) {
        FoodRating foodRating = new FoodRating();
        foodRating.setUserId(foodRatingRequestDto.getUserId());
        foodRating.setRating(foodRatingRequestDto.getRating());
        foodRating.setComment(foodRatingRequestDto.getComment());
        foodRating.setRatingDate(LocalDateTime.now());  // Set the current date and time
        return foodRating;
    }

    public FoodRatingResponseDto getFoodRatingById(Long id) {
        FoodRating foodRating = foodRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodRating not found with id " + id));
        return mapToDto(foodRating);
    }

    public void deleteFoodRating(Long id) {
        if (!foodRatingRepository.existsById(id)) {
            throw new ResourceNotFoundException("FoodRating not found with id " + id);
        }
        foodRatingRepository.deleteById(id);
    }

    public List<FoodRatingResponseDto> getFoodRatingsByFoodId(Long foodId) {
        List<FoodRating> foodRatings = foodRatingRepository.findByFoodFoodId(foodId);
        return foodRatings.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public FoodRatingResponseDto updateFoodRating(Long id, FoodRatingRequestDto foodRatingRequestDto) {
        FoodRating foodRating = foodRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodRating not found with id " + id));
        foodRating.setUserId(foodRatingRequestDto.getUserId());
        foodRating.setRating(foodRatingRequestDto.getRating());
        foodRating.setComment(foodRatingRequestDto.getComment());
        foodRating = foodRatingRepository.save(foodRating);
        return mapToDto(foodRating);
    }

    public FoodRatingResponseDto parchFoodRating(Long id, FoodRatingRequestDto foodRatingRequestDto) {
        FoodRating foodRating = foodRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodRating not found with id " + id));

        if (foodRatingRequestDto.getUserId() != null) {
            foodRating.setUserId(foodRatingRequestDto.getUserId());
        } else {
            foodRating.setUserId(foodRating.getUserId());
        }

        if (foodRatingRequestDto.getRating() != null) {
            foodRating.setRating(foodRatingRequestDto.getRating());
        } else {
            foodRating.setRating(foodRating.getRating());
        }

        if (foodRatingRequestDto.getComment() != null) {
            foodRating.setComment(foodRatingRequestDto.getComment());
        } else {
            foodRating.setComment(foodRating.getComment());
        }

        foodRating = foodRatingRepository.save(foodRating);
        return mapToDto(foodRating);
    }

    //MÉTODO O PETICIONES CRUZADAS
    public List<FoodRatingDTO> getFoodRatingsByUserId(Long userId) {
        List<FoodRating> foodRatings = foodRatingRepository.findByUserUserId(userId);
        return foodRatings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private FoodRatingDTO convertToDTO(FoodRating foodRating) {
        FoodRatingDTO foodRatingDTO = new FoodRatingDTO();
        foodRatingDTO.setRatingId(foodRating.getRatingId());
        foodRatingDTO.setRating(foodRating.getRating());
        foodRatingDTO.setComment(foodRating.getComment());
        foodRatingDTO.setFoodId(foodRating.getFood().getFoodId());
        foodRatingDTO.setUserId(foodRating.getUser().getUserId());
        return foodRatingDTO;
    }

}