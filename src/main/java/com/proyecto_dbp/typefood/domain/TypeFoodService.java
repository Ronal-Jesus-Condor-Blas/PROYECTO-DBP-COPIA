package com.proyecto_dbp.typefood.domain;

import com.proyecto_dbp.typefood.dto.TypeFoodRequestDto;
import com.proyecto_dbp.typefood.dto.TypeFoodResponseDto;
import com.proyecto_dbp.typefood.infrastructure.TypeFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TypeFoodService {

    @Autowired
    private TypeFoodRepository typeFoodRepository;

    public TypeFoodResponseDto getTypeFoodById(Long id) {
        Optional<TypeFood> typeFood = typeFoodRepository.findById(id);
        return typeFood.map(this::mapToDto).orElse(null);
    }

    public List<TypeFoodResponseDto> getAllTypeFoods() {
        List<TypeFood> typeFoods = typeFoodRepository.findAll();
        return typeFoods.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public TypeFoodResponseDto createTypeFood(TypeFoodRequestDto typeFoodRequestDto) {
        TypeFood typeFood = mapToEntity(typeFoodRequestDto);
        typeFood = typeFoodRepository.save(typeFood);
        return mapToDto(typeFood);
    }

    public TypeFoodResponseDto updateTypeFood(Long id, TypeFoodRequestDto typeFoodRequestDto) {
        Optional<TypeFood> typeFoodOptional = typeFoodRepository.findById(id);
        if (typeFoodOptional.isPresent()) {
            TypeFood typeFood = typeFoodOptional.get();
            typeFood.setType(typeFoodRequestDto.getType());
            typeFood = typeFoodRepository.save(typeFood);
            return mapToDto(typeFood);
        }
        return null;
    }

    public void deleteTypeFood(Long id) {
        typeFoodRepository.deleteById(id);
    }

    private TypeFoodResponseDto mapToDto(TypeFood typeFood) {
        TypeFoodResponseDto typeFoodResponseDto = new TypeFoodResponseDto();
        typeFoodResponseDto.setTypeFoodId(typeFood.getTypeFoodId());
        typeFoodResponseDto.setType(typeFood.getType());
        return typeFoodResponseDto;
    }

    private TypeFood mapToEntity(TypeFoodRequestDto typeFoodRequestDto) {
        TypeFood typeFood = new TypeFood();
        typeFood.setType(typeFoodRequestDto.getType());
        return typeFood;
    }
}