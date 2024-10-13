package com.proyecto_dbp.foodrating.application;

import com.proyecto_dbp.foodrating.domain.FoodRatingService;
import com.proyecto_dbp.foodrating.dto.FoodRatingDTO;
import com.proyecto_dbp.foodrating.dto.FoodRatingRequestDto;
import com.proyecto_dbp.foodrating.dto.FoodRatingResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foodratings")
public class FoodRatingController {

    //Hola
    @Autowired
    private FoodRatingService foodRatingService;

    @GetMapping("/{id}")
    public ResponseEntity<FoodRatingResponseDto> getFoodRatingById(@PathVariable Long id) {
        FoodRatingResponseDto foodRating = foodRatingService.getFoodRatingById(id);
        if (foodRating != null) {
            return ResponseEntity.ok(foodRating);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/food/{foodId}")
    public ResponseEntity<List<FoodRatingResponseDto>> getFoodRatingsByFoodId(@PathVariable Long foodId) {
        List<FoodRatingResponseDto> foodRatings = foodRatingService.getFoodRatingsByFoodId(foodId);
        return ResponseEntity.ok(foodRatings);
    }

    @PostMapping
    public ResponseEntity<FoodRatingResponseDto> createFoodRating(@RequestBody FoodRatingRequestDto foodRatingRequestDto) {
        FoodRatingResponseDto createdFoodRating = foodRatingService.createFoodRating(foodRatingRequestDto);
        return ResponseEntity.ok(createdFoodRating);
    }

    //patch
    @PatchMapping("/{id}")
    public ResponseEntity<FoodRatingResponseDto> parchFoodRating(@PathVariable Long id, @RequestBody FoodRatingRequestDto foodRatingRequestDto) {
        FoodRatingResponseDto updatedFoodRating = foodRatingService.parchFoodRating(id, foodRatingRequestDto);
        if (updatedFoodRating != null) {
            return ResponseEntity.ok(updatedFoodRating);
        }
        return ResponseEntity.notFound().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<FoodRatingResponseDto> updateFoodRating(@PathVariable Long id, @RequestBody FoodRatingRequestDto foodRatingRequestDto) {
        FoodRatingResponseDto updatedFoodRating = foodRatingService.updateFoodRating(id, foodRatingRequestDto);
        if (updatedFoodRating != null) {
            return ResponseEntity.ok(updatedFoodRating);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodRating(@PathVariable Long id) {
        foodRatingService.deleteFoodRating(id);
        return ResponseEntity.noContent().build();
    }

    //MÉTODO O PETICIONES CRUZADAS
    @GetMapping("/users/{userId}/foodratings")
    public List<FoodRatingDTO> getFoodRatingsByUserId(@PathVariable Long userId) {
        return foodRatingService.getFoodRatingsByUserId(userId);
    }

}