package com.proyecto_dbp.typefood.application;

import com.proyecto_dbp.typefood.domain.TypeFoodService;
import com.proyecto_dbp.typefood.dto.TypeFoodDTO;
import com.proyecto_dbp.typefood.dto.TypeFoodRequestDto;
import com.proyecto_dbp.typefood.dto.TypeFoodResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/typefoods")
public class TypeFoodController {

    @Autowired
    private TypeFoodService typeFoodService;

    @GetMapping("/{id}")
    public ResponseEntity<TypeFoodResponseDto> getTypeFoodById(@PathVariable Long id) {
        TypeFoodResponseDto typeFood = typeFoodService.getTypeFoodById(id);
        if (typeFood != null) {
            return ResponseEntity.ok(typeFood);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TypeFoodResponseDto>> getAllTypeFoods() {
        List<TypeFoodResponseDto> typeFoods = typeFoodService.getAllTypeFoods();
        return ResponseEntity.ok(typeFoods);
    }

    @PostMapping
    public ResponseEntity<TypeFoodResponseDto> createTypeFood(@RequestBody TypeFoodRequestDto typeFoodRequestDto) {
        TypeFoodResponseDto createdTypeFood = typeFoodService.createTypeFood(typeFoodRequestDto);
        return ResponseEntity.ok(createdTypeFood);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TypeFoodResponseDto> parchTypeFood(@PathVariable Long id, @RequestBody TypeFoodRequestDto typeFoodRequestDto) {
        TypeFoodResponseDto updatedTypeFood = typeFoodService.parchTypeFood(id, typeFoodRequestDto);
        if (updatedTypeFood != null) {
            return ResponseEntity.ok(updatedTypeFood);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeFoodResponseDto> updateTypeFood(@PathVariable Long id, @RequestBody TypeFoodRequestDto typeFoodRequestDto) {
        TypeFoodResponseDto updatedTypeFood = typeFoodService.updateTypeFood(id, typeFoodRequestDto);
        if (updatedTypeFood != null) {
            return ResponseEntity.ok(updatedTypeFood);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeFood(@PathVariable Long id) {
        typeFoodService.deleteTypeFood(id);
        return ResponseEntity.noContent().build();
    }

    //MÉTODOS O PETICIONES CRUZADAS


}