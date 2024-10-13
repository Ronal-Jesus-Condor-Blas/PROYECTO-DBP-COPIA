package com.proyecto_dbp.typefood.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class TypeFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeFoodId;

    @NotNull
    @Size(max = 100)
    private String type; // Tipo de comida (e.g. italiana, mexicana, peruana)

    private String description; // Descripción del tipo de comida
    // Getters and Setters
}
