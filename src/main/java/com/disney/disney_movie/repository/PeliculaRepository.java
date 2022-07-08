package com.disney.disney_movie.repository;

import com.disney.disney_movie.entity.Genero;
import com.disney.disney_movie.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    List<Pelicula> findByTituloContainingIgnoreCase(String titulo);
    List<Pelicula> findAllByOrderByIdDesc();
    List<Pelicula> findAllByOrderByIdAsc();
    Pelicula findByGeneroAndTituloContainingIgnoreCase(Genero genero, String titulo);
    List<Pelicula> findByGenero(Genero genero);

}
