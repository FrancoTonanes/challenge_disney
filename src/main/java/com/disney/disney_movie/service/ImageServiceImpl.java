package com.disney.disney_movie.service;

import com.disney.disney_movie.dto.GeneroDTO;
import com.disney.disney_movie.dto.PeliculaDTO;
import com.disney.disney_movie.dto.PersonajeDTO;
import com.disney.disney_movie.entity.Genero;
import com.disney.disney_movie.entity.Pelicula;
import com.disney.disney_movie.entity.Personaje;
import com.disney.disney_movie.exception.NotFoundException;
import com.disney.disney_movie.repository.GeneroRepository;
import com.disney.disney_movie.repository.PeliculaRepository;
import com.disney.disney_movie.repository.PersonajeRepository;
import com.disney.disney_movie.service.interfaces.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String PATH_MOVIE = "src//main//resources//static/images/movies";

    private static final String PATH_CHARACTER = "src//main//resources//static/images/characters";

    private static final String PATH_GENDER = "src//main//resources//static/images/genders";

    private void upload(MultipartFile file, String path){
        Path relativePath = Paths.get(path);
        String absolutePath = relativePath.toFile().getAbsolutePath();
        try {
            byte[] bytes = file.getBytes();
            Path completePath = Paths.get(absolutePath + "//" + file.getOriginalFilename());
            Files.write(completePath, bytes);
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
    }

    @Override
    public PeliculaDTO updateMovie(MultipartFile file, Long id) {
        Pelicula peliculaDB = peliculaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pelicula no encontrada"));

        upload(file, PATH_MOVIE);
        peliculaDB.setImagen(file.getOriginalFilename());
        return modelMapper.map(peliculaRepository.save(peliculaDB), PeliculaDTO.class);
    }

    @Override
    public PersonajeDTO updateCharacter(MultipartFile file, Long id) {
        Personaje personajeDB = personajeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Personaje no encontrado"));
        upload(file, PATH_CHARACTER);
        personajeDB.setImagen(file.getOriginalFilename());
        return modelMapper.map(personajeRepository.save(personajeDB), PersonajeDTO.class);
    }

    @Override
    public GeneroDTO updateGender(MultipartFile file, Long id) {
        Genero generoDB = generoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genero no encontrado"));
        upload(file, PATH_GENDER);
        generoDB.setImagen(file.getOriginalFilename());
        return modelMapper.map(generoRepository.save(generoDB), GeneroDTO.class);
    }
}
