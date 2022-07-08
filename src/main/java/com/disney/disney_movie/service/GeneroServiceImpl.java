package com.disney.disney_movie.service;

import com.disney.disney_movie.dto.GeneroDTO;
import com.disney.disney_movie.entity.Genero;
import com.disney.disney_movie.exception.NotFoundException;
import com.disney.disney_movie.repository.GeneroRepository;
import com.disney.disney_movie.repository.PeliculaRepository;
import com.disney.disney_movie.service.interfaces.GeneroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PeliculaRepository peliculaRepository;

    @Override
    public List<GeneroDTO> getAllGenero() {
        return generoRepository.findAll()
                .stream()
                .map( p -> modelMapper.map(p, GeneroDTO.class)).collect(Collectors.toList());
    }

    @Override
    public GeneroDTO getGeneroById(Long idGenre) {
        return modelMapper.map(generoRepository.findById(idGenre)
                .orElseThrow(() -> new NotFoundException("Genero no encontrado")), GeneroDTO.class);
    }

    @Override
    public GeneroDTO postGenero(GeneroDTO genero) {
        Genero generoDB = generoRepository.findByNombreContainingIgnoreCase(genero.getNombre());

        if (generoDB != null){
            return modelMapper.map(generoDB, GeneroDTO.class);
        }
        genero.setPeliculas(new ArrayList<>());
        generoDB = generoRepository.save(modelMapper.map(genero, Genero.class));

        return modelMapper.map(generoDB, GeneroDTO.class);
    }

    @Override
    public GeneroDTO putGenero(Long id, GeneroDTO genero) {
        Genero generoDB = generoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Genero no encontrado"));

        generoDB.setImagen(genero.getImagen());
        generoDB.setNombre(genero.getNombre());

        return modelMapper.map(generoRepository.save(generoDB), GeneroDTO.class);
    }


    @Override
    public Void deleteGenero(Long idGenre) {
        Genero generoDB = generoRepository.findById(idGenre)
                .orElseThrow(() -> new NotFoundException("Genero no encontrado"));
        generoRepository.delete(generoDB);
        return null;
    }
}
