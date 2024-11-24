package com.proyecto_dbp.restaurant.application;

import com.proyecto_dbp.email.HelloEmailEvent;
import com.proyecto_dbp.email.NewRestaurantEvent;
import com.proyecto_dbp.restaurant.domain.RestaurantService;
import com.proyecto_dbp.restaurant.dto.RestaurantRequestDto;
import com.proyecto_dbp.restaurant.dto.RestaurantResponseDto;
import com.proyecto_dbp.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    //pata mandar correos usando eventos
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        RestaurantResponseDto restaurant = restaurantService.getRestaurantById(id);
        if (restaurant != null) {
            return ResponseEntity.ok(restaurant);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        List<RestaurantResponseDto> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<RestaurantResponseDto> createRestaurant(
            @RequestPart("restaurant") RestaurantRequestDto restaurantRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        RestaurantResponseDto createdRestaurant = restaurantService.createRestaurant(restaurantRequestDto, image);

        // Enviar correo de bienvenida
        applicationEventPublisher.publishEvent(
                new NewRestaurantEvent(
                        createdRestaurant.getRestaurantId().toString(),
                        createdRestaurant.getEmail(),
                        createdRestaurant.getName(),
                        createdRestaurant.getLatitude().toString() + ", " + createdRestaurant.getLongitude().toString()
                )
        );

        return ResponseEntity.ok(createdRestaurant);
    }


    //**
    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> parchRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDto restaurantRequestDto) {
        RestaurantResponseDto updatedRestaurant = restaurantService.parchRestaurant(id, restaurantRequestDto);
        if (updatedRestaurant != null) {
            return ResponseEntity.ok(updatedRestaurant);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(
            @PathVariable Long id,
            @RequestPart("restaurant") RestaurantRequestDto restaurantRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        RestaurantResponseDto updatedRestaurant = restaurantService.updateRestaurant(id, restaurantRequestDto, image);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}