package com.disney.disney_movie.service.interfaces;

import com.disney.disney_movie.dto.PeliculaDTO;
import com.disney.disney_movie.dto.PeliculaShortDTO;


import java.util.List;
import java.util.Map;

public interface PeliculaService {
    List<PeliculaShortDTO> getPeliculaByTitulo(String titulo);
    List<PeliculaShortDTO> getPeliculaByGenero(String idGenero);
    List<PeliculaShortDTO> getPeliculaByOrden(String orden);
    PeliculaDTO getPeliculaDetailsById(Long idMovie);
    List<PeliculaDTO> getAllPeliculaDetails();
    PeliculaDTO createPelicula(PeliculaDTO pelicula);
    PeliculaDTO createPelicula(PeliculaDTO pelicula, Long idGenre);

    PeliculaDTO putPelicula(Long idPelicula, PeliculaDTO pelicula);

    PeliculaDTO patchPelicula(Long idPelicula, Map<String, ?> pelicula);

    Void deletePelicula(Long id);
    List<PeliculaShortDTO> getAllPeliculas();

    PeliculaDTO addPersonajeToPelicula(Long idMovie, Long idPersonaje);
    PeliculaDTO deletePersonajeFromPelicula(Long idMovie, Long idPersonaje);

}
