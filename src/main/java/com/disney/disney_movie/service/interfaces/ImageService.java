package com.disney.disney_movie.service.interfaces;


import com.disney.disney_movie.dto.GeneroDTO;
import com.disney.disney_movie.dto.PeliculaDTO;
import com.disney.disney_movie.dto.PersonajeDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    PeliculaDTO updateMovie(MultipartFile file, Long id);
    PersonajeDTO updateCharacter(MultipartFile file, Long id);
    GeneroDTO updateGender(MultipartFile file, Long id);
}