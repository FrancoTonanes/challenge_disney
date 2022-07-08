package com.disney.disney_movie.repository;

import com.disney.disney_movie.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
    List<Personaje> findByNombreContainingIgnoreCase(String nombre);
    Personaje findByEdadAndNombreContainingIgnoreCase(Integer edad, String nombre);
    List<Personaje> findByEdad(Integer edad);

}
