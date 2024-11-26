package com.proyecto_dbp.food.application;

import com.proyecto_dbp.food.domain.FoodService;
import com.proyecto_dbp.food.dto.FoodDTO;
import com.proyecto_dbp.food.dto.FoodPatchRequestDto;
import com.proyecto_dbp.food.dto.FoodRequestDto;
import com.proyecto_dbp.food.dto.FoodResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponseDto> getFoodById(@PathVariable Long id) {
        FoodResponseDto food = foodService.getFoodById(id);
        if (food != null) {
            return ResponseEntity.ok(food);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<FoodResponseDto>> getAllFoods() {
        List<FoodResponseDto> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<FoodResponseDto> createFood(
            @RequestPart("food") FoodRequestDto foodRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
            )
    {
        FoodResponseDto createdFood = foodService.createFood(foodRequestDto, image);
        return ResponseEntity.ok(createdFood);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FoodResponseDto> parchFood(@PathVariable Long id, @RequestBody FoodRequestDto foodRequestDto) {
        FoodResponseDto updatedFood = foodService.parchFFood(id, new FoodPatchRequestDto());
        if (updatedFood != null) {
            return ResponseEntity.ok(updatedFood);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<FoodResponseDto> updateFood(
            @PathVariable Long id,
            @RequestPart("food") FoodRequestDto foodRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        FoodResponseDto updatedFood = foodService.updateFood(id, foodRequestDto, image);
        return ResponseEntity.ok(updatedFood);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }

    //MÉTODOS CRUZADOS
    @GetMapping("/restaurants/{restaurantId}/foods")
    public List<FoodDTO> getFoodsByRestaurantId(@PathVariable Long restaurantId) {
        return foodService.getFoodsByRestaurantId(restaurantId);
    }
}