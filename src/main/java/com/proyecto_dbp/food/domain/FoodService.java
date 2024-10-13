package com.proyecto_dbp.food.domain;

import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.food.dto.FoodDTO;
import com.proyecto_dbp.food.dto.FoodPatchRequestDto;
import com.proyecto_dbp.food.dto.FoodRequestDto;
import com.proyecto_dbp.food.dto.FoodResponseDto;
import com.proyecto_dbp.food.infrastructure.FoodRepository;
import com.proyecto_dbp.restaurant.domain.Restaurant;
import com.proyecto_dbp.restaurant.infrastructure.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public FoodResponseDto getFoodById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id " + id));
        return mapToDto(food);
    }

    public List<FoodResponseDto> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public FoodResponseDto createFood(FoodRequestDto foodRequestDto) {
        if (foodRequestDto.getName() == null || foodRequestDto.getName().isEmpty()) {
            throw new ValidationException("Name cannot be null or empty");
        }
        Food food = mapToEntity(foodRequestDto);
        food = foodRepository.save(food);
        return mapToDto(food);
    }

    public FoodResponseDto updateFood(Long id, FoodRequestDto foodRequestDto) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id " + id));
        if (foodRequestDto.getName() == null || foodRequestDto.getName().isEmpty()) {
            throw new ValidationException("Name cannot be null or empty");
        }
        food.setName(foodRequestDto.getName());
        food.setPrice(foodRequestDto.getPrice());
        food.setStatus(foodRequestDto.getStatus());
        food.setRestaurant(getRestaurantById(foodRequestDto.getRestaurantId()));
        food = foodRepository.save(food);
        return mapToDto(food);
    }

    public void deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new ResourceNotFoundException("Food not found with id " + id);
        }
        foodRepository.deleteById(id);
    }

    private FoodResponseDto mapToDto(Food food) {
        FoodResponseDto foodResponseDto = new FoodResponseDto();
        foodResponseDto.setFoodId(food.getFoodId());
        foodResponseDto.setName(food.getName());
        foodResponseDto.setPrice(food.getPrice());
        foodResponseDto.setStatus(food.getStatus());
        //foodResponseDto.setAverageRating(food.getAverageRating());
        foodResponseDto.setCreatedDate(food.getCreatedDate());
        return foodResponseDto;
    }

    private Food mapToEntity(FoodRequestDto foodRequestDto) {
        Food food = new Food();
        food.setName(foodRequestDto.getName());
        food.setPrice(foodRequestDto.getPrice());
        food.setStatus(foodRequestDto.getStatus());
        food.setRestaurant(getRestaurantById(foodRequestDto.getRestaurantId()));  // Set the restaurant
        //**
        food.setCreatedDate(LocalDateTime.now());
        return food;
    }

    private Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + restaurantId));
    }

    //parch
    public FoodResponseDto parchFFood(Long id, FoodPatchRequestDto foodRequestDto) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id " + id));

        if (foodRequestDto.getName() != null) {
            food.setName(foodRequestDto.getName());
        } else {
            food.setName(food.getName());
        }

        if (foodRequestDto.getPrice() != null) {
            food.setPrice(foodRequestDto.getPrice());
        } else {
            food.setPrice(food.getPrice());
        }


        if (foodRequestDto.getStatus() != null) {
            String statusStr = foodRequestDto.getStatus().toUpperCase();
            FoodStatus status;

                //if statusStr is not 'AVAILABLE' or 'UNAVAILABLE' it will throw an exception before to set the status
                if (!statusStr.equals("AVAILABLE") && !statusStr.equals("UNAVAILABLE")) {
                    throw new ValidationException("Status must be either 'AVAILABLE' or 'UNAVAILABLE'");
                }
                status = FoodStatus.valueOf(statusStr);
            food.setStatus(status);
        } else {
            food.setStatus(food.getStatus());
        }

        if (foodRequestDto.getRestaurantId() != null) {
            food.setRestaurant(getRestaurantById(foodRequestDto.getRestaurantId()));
        } else {
            food.setRestaurant(food.getRestaurant());
        }

        food = foodRepository.save(food);
        return mapToDto(food);
    }

    //Métodos cruzados
    public List<FoodDTO> getFoodsByRestaurantId(Long restaurantId) {
        List<Food> foods = foodRepository.findByRestaurantRestaurantId(restaurantId);
        return foods.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private FoodDTO convertToDTO(Food food) {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setFoodId(food.getFoodId());
        foodDTO.setName(food.getName());
        foodDTO.setPrice(food.getPrice());
        foodDTO.setAverageRating(food.getAverageRating());
        foodDTO.setStatus(food.getStatus().name());
        return foodDTO;
    }
}
