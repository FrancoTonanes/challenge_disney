package com.disney.disney_movie.repository;

import com.disney.disney_movie.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    Genero findByNombreContainingIgnoreCase(String nombre);
}
