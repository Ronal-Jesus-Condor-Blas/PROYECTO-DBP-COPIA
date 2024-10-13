// src/main/java/com/proyecto_dbp/restaurantrating/application/RestaurantRatingController.java
package com.proyecto_dbp.restaurantrating.application;

import com.proyecto_dbp.restaurantrating.domain.RestaurantRatingService;
import com.proyecto_dbp.restaurantrating.dto.RestaurantRatingDTO;
import com.proyecto_dbp.restaurantrating.dto.RestaurantRatingRequestDto;
import com.proyecto_dbp.restaurantrating.dto.RestaurantRatingResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantratings")
public class RestaurantRatingController {

    @Autowired
    private RestaurantRatingService restaurantRatingService;

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantRatingResponseDto> getRestaurantRatingById(@PathVariable Long id) {
        RestaurantRatingResponseDto restaurantRating = restaurantRatingService.getRestaurantRatingById(id);
        if (restaurantRating != null) {
            return ResponseEntity.ok(restaurantRating);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<RestaurantRatingResponseDto>> getRestaurantRatingsByRestaurantId(@PathVariable Long restaurantId) {
        List<RestaurantRatingResponseDto> restaurantRatings = restaurantRatingService.getRestaurantRatingsByRestaurantId(restaurantId);
        return ResponseEntity.ok(restaurantRatings);
    }

    @PostMapping
    public ResponseEntity<RestaurantRatingResponseDto> createRestaurantRating(@RequestBody RestaurantRatingRequestDto restaurantRatingRequestDto) {
        RestaurantRatingResponseDto createdRestaurantRating = restaurantRatingService.createRestaurantRating(restaurantRatingRequestDto);
        return ResponseEntity.ok(createdRestaurantRating);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantRatingResponseDto> parchRestaurantRating(@PathVariable Long id, @RequestBody RestaurantRatingRequestDto restaurantRatingRequestDto) {
        RestaurantRatingResponseDto updatedRestaurantRating = restaurantRatingService.parchRestaurantRating(id, restaurantRatingRequestDto);
        if (updatedRestaurantRating != null) {
            return ResponseEntity.ok(updatedRestaurantRating);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantRatingResponseDto> updateRestaurantRating(@PathVariable Long id, @RequestBody RestaurantRatingRequestDto restaurantRatingRequestDto) {
        RestaurantRatingResponseDto updatedRestaurantRating = restaurantRatingService.updateRestaurantRating(id, restaurantRatingRequestDto);
        if (updatedRestaurantRating != null) {
            return ResponseEntity.ok(updatedRestaurantRating);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurantRating(@PathVariable Long id) {
        restaurantRatingService.deleteRestaurantRating(id);
        return ResponseEntity.noContent().build();
    }

    //MPETODO O PETICIONES CRUZADAS
    @GetMapping("/users/{userId}/restaurantratings")
    public List<RestaurantRatingDTO> getRestaurantRatingsByUserId(@PathVariable Long userId) {
        return restaurantRatingService.getRestaurantRatingsByUserId(userId);
    }

}