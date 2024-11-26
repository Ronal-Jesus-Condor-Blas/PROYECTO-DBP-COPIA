package com.proyecto_dbp.restaurant.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.food.domain.Food;
import com.proyecto_dbp.restaurant.dto.RestaurantRequestDto;
import com.proyecto_dbp.restaurant.dto.RestaurantResponseDto;
import com.proyecto_dbp.restaurant.infrastructure.RestaurantRepository;
import com.proyecto_dbp.restaurantrating.domain.RestaurantRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    private final WebClient webClient;

    @Autowired
    public RestaurantService(WebClient webClient) {
        this.webClient = webClient;
    }

    public RestaurantResponseDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));
        return mapToDto(restaurant);
    }

    public List<RestaurantResponseDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto, MultipartFile image) {
        if (restaurantRepository.findByEmail(restaurantRequestDto.getEmail()).isPresent()) {
            throw new ValidationException("Email already in use");
        }

        // Subir imagen si está presente
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = uploadImage(image, "restaurant");
        }

        Restaurant restaurant = mapToEntity(restaurantRequestDto);
        restaurant.setImage(imageUrl); // Guardar la URL de la imagen en el restaurante

        restaurant = restaurantRepository.save(restaurant);
        return mapToDto(restaurant);
    }

    public RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto restaurantRequestDto, MultipartFile image) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));

        if (restaurantRequestDto.getName() != null) {
            restaurant.setName(restaurantRequestDto.getName());
        }
        if (restaurantRequestDto.getLatitude() != null) {
            restaurant.setLatitude(restaurantRequestDto.getLatitude());
        }
        if (restaurantRequestDto.getLongitude() != null) {
            restaurant.setLongitude(restaurantRequestDto.getLongitude());
        }
        if (restaurantRequestDto.getStatus() != null) {
            restaurant.setStatus(restaurantRequestDto.getStatus());
        }
        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadImage(image, "restaurant");
            restaurant.setImage(imageUrl);
        }

        if (restaurantRequestDto.getEmail() != null) {
            restaurant.setEmail(restaurantRequestDto.getEmail());
        }

        restaurant = restaurantRepository.save(restaurant);
        return mapToDto(restaurant);
    }


    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant not found with id " + id);
        }
        restaurantRepository.deleteById(id);
    }

    private String uploadImage(MultipartFile image, String type) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("type", type);
            requestBody.put("image", base64Image);

            String response = webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(response);

            String bodyContent = responseJson.get("body").asText();
            JsonNode bodyJson = objectMapper.readTree(bodyContent);

            return bodyJson.get("image_url").asText();
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image", e);
        }
    }

    private RestaurantResponseDto mapToDto(Restaurant restaurant) {
        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();
        restaurantResponseDto.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponseDto.setName(restaurant.getName());
        restaurantResponseDto.setLatitude(restaurant.getLatitude());
        restaurantResponseDto.setLongitude(restaurant.getLongitude());
        restaurantResponseDto.setImage(restaurant.getImage());
        restaurantResponseDto.setStatus(restaurant.getStatus());
        //restaurantResponseDto.setAverageRating(restaurant.getAverageRating());
        restaurantResponseDto.setCreatedDate(restaurant.getCreatedDate());
        restaurantResponseDto.setEmail(restaurant.getEmail());
        return restaurantResponseDto;
    }

    private Restaurant mapToEntity(RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequestDto.getName());
        restaurant.setLatitude(restaurantRequestDto.getLatitude());
        restaurant.setLongitude(restaurantRequestDto.getLongitude());
        restaurant.setStatus(restaurantRequestDto.getStatus());
        restaurant.setEmail(restaurantRequestDto.getEmail());
        restaurant.setCreatedDate(LocalDateTime.now());
        return restaurant;
    }

    //parch
    public RestaurantResponseDto parchRestaurant(Long id, RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));

        if (restaurantRequestDto.getName() != null && !restaurantRequestDto.getName().isEmpty()) {
            restaurant.setName(restaurantRequestDto.getName());
        } else {
            restaurant.setName(restaurant.getName());
        }

        if (restaurantRequestDto.getLatitude() != null) {
            restaurant.setLatitude(restaurantRequestDto.getLatitude());
        } else {
            restaurant.setLatitude(restaurant.getLatitude());
        }

        if (restaurantRequestDto.getLongitude() != null) {
            restaurant.setLongitude(restaurantRequestDto.getLongitude());
        } else {
            restaurant.setLongitude(restaurant.getLongitude());
        }

        if (restaurantRequestDto.getStatus() != null) {
            restaurant.setStatus(restaurantRequestDto.getStatus());
        } else {
            restaurant.setStatus(restaurant.getStatus());
        }

        restaurant = restaurantRepository.save(restaurant);
        return mapToDto(restaurant);
    }

}