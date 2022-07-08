package com.disney.disney_movie.service.interfaces;

import com.disney.disney_movie.dto.GeneroDTO;

import java.util.List;

public interface GeneroService {

    List<GeneroDTO> getAllGenero();
    GeneroDTO getGeneroById(Long idGenre);
    GeneroDTO postGenero(GeneroDTO genero);
    GeneroDTO putGenero(Long id, GeneroDTO genero);

    Void deleteGenero(Long idGenre);
}
