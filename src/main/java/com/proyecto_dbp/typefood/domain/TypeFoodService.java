package com.proyecto_dbp.typefood.domain;

import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.food.domain.Food;
import com.proyecto_dbp.food.dto.FoodRequestDto;
import com.proyecto_dbp.food.dto.FoodResponseDto;
import com.proyecto_dbp.restaurant.domain.Restaurant;
import com.proyecto_dbp.restaurant.infrastructure.RestaurantRepository;
import com.proyecto_dbp.typefood.dto.TypeFoodRequestDto;
import com.proyecto_dbp.typefood.dto.TypeFoodResponseDto;
import com.proyecto_dbp.typefood.infrastructure.TypeFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeFoodService {

    @Autowired
    private TypeFoodRepository typeFoodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public TypeFoodResponseDto getTypeFoodById(Long id) {
        TypeFood typeFood = typeFoodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypeFood not found with id " + id));
        return mapToDto(typeFood);
    }

    public List<TypeFoodResponseDto> getAllTypeFoods() {
        List<TypeFood> typeFoods = typeFoodRepository.findAll();
        return typeFoods.stream().map(this::mapToDto).collect(Collectors.toList());
    }



    public TypeFoodResponseDto updateTypeFood(Long id, TypeFoodRequestDto typeFoodRequestDto) {
        TypeFood typeFood = typeFoodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypeFood not found with id " + id));
        if (typeFoodRequestDto.getType() == null || typeFoodRequestDto.getType().isEmpty()) {
            throw new ValidationException("Type cannot be null or empty");
        }
        typeFood.setType(typeFoodRequestDto.getType());
        typeFood = typeFoodRepository.save(typeFood);
        return mapToDto(typeFood);
    }

    public void deleteTypeFood(Long id) {
        if (!typeFoodRepository.existsById(id)) {
            throw new ResourceNotFoundException("TypeFood not found with id " + id);
        }
        typeFoodRepository.deleteById(id);
    }

    public List<TypeFood> getTypesOfFoodByRestaurantId(Long restaurantId) {
        return typeFoodRepository.findByRestaurantRestaurantId(restaurantId);
    }



    public TypeFoodResponseDto patchTypeFood(Long id, TypeFoodRequestDto typeFoodRequestDto) {
        TypeFood typeFood = typeFoodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypeFood not found with id " + id));

        if (typeFoodRequestDto.getType() != null && !typeFoodRequestDto.getType().isEmpty()) {
            typeFood.setType(typeFoodRequestDto.getType());
        }

        if (typeFoodRequestDto.getDescription() != null && !typeFoodRequestDto.getDescription().isEmpty()) {
            typeFood.setDescription(typeFoodRequestDto.getDescription());
        }

        typeFood = typeFoodRepository.save(typeFood);
        return mapToDto(typeFood);
    }

    //
    //DE NEUVO
    public TypeFoodResponseDto createTypeFood(TypeFoodRequestDto typeFoodRequestDto) {
        if (typeFoodRequestDto.getType() == null || typeFoodRequestDto.getType().isEmpty()) {
            throw new ValidationException("Type cannot be null or empty");
        }
        TypeFood typeFood = mapToEntity(typeFoodRequestDto);
        typeFood = typeFoodRepository.save(typeFood);
        return mapToDto(typeFood);
    }

    private TypeFood mapToEntity(TypeFoodRequestDto typeFoodRequestDto) {
        TypeFood typeFood = new TypeFood();
        typeFood.setType(typeFoodRequestDto.getType());
        typeFood.setDescription(typeFoodRequestDto.getDescription());
        typeFood.setRestaurant(getRestaurantById(typeFoodRequestDto.getRestaurantId()));  // Set the restaurant
        return typeFood;
    }

    private Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + restaurantId));
    }

    private TypeFoodResponseDto mapToDto(TypeFood typeFood) {
        TypeFoodResponseDto typeFoodResponseDto = new TypeFoodResponseDto();
        typeFoodResponseDto.setTypeFoodId(typeFood.getTypeFoodId());
        typeFoodResponseDto.setType(typeFood.getType());
        typeFoodResponseDto.setDescription(typeFood.getDescription());
        // Add any additional fields if necessary
        return typeFoodResponseDto;
    }

}