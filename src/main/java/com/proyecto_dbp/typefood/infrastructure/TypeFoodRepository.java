package com.proyecto_dbp.typefood.infrastructure;

import com.proyecto_dbp.typefood.domain.TypeFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFoodRepository extends JpaRepository<TypeFood, Long> {
}