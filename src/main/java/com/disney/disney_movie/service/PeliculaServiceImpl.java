package com.disney.disney_movie.service;

import com.disney.disney_movie.dto.PeliculaDTO;
import com.disney.disney_movie.dto.PeliculaShortDTO;
import com.disney.disney_movie.entity.Genero;
import com.disney.disney_movie.entity.Pelicula;
import com.disney.disney_movie.entity.Personaje;
import com.disney.disney_movie.exception.NotFoundException;
import com.disney.disney_movie.repository.GeneroRepository;
import com.disney.disney_movie.repository.PeliculaRepository;
import com.disney.disney_movie.repository.PersonajeRepository;
import com.disney.disney_movie.service.interfaces.PeliculaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PeliculaShortDTO> getPeliculaByTitulo(String titulo) {

        List<Pelicula> peliculas = peliculaRepository.findByTituloContainingIgnoreCase(titulo);

        if (peliculas.isEmpty()){
            return null;
        }

        List<PeliculaShortDTO> peliculaShortDTOS = new ArrayList<>();

        peliculas.stream().forEach( ( p ) -> {
            PeliculaShortDTO pelicula = modelMapper.map(p, PeliculaShortDTO.class);
            peliculaShortDTOS.add(pelicula);
        });

        return peliculaShortDTOS;
    }

    @Override
    public List<PeliculaShortDTO> getPeliculaByGenero(String idGenero) {
        Genero genero = generoRepository.findById(Long.valueOf(idGenero)).orElse(null);

        if (genero != null){
            List<Pelicula> peliculas = genero.getPeliculas();
            List<PeliculaShortDTO> peliculaShortDTOList = new ArrayList<>();
            peliculas.stream().forEach( ( p ) -> {
                PeliculaShortDTO peliculaShortDto = modelMapper.map(p, PeliculaShortDTO.class);
                peliculaShortDTOList.add(peliculaShortDto);
            });
            return peliculaShortDTOList;
        }
        return null;
    }

    @Override
    public List<PeliculaShortDTO> getPeliculaByOrden(String genero) {
        List<Pelicula> peliculas = new ArrayList<>();

        if (genero.equalsIgnoreCase("ASC")){
            peliculas = peliculaRepository.findAllByOrderByIdAsc();
        }
        else {
            peliculas = peliculaRepository.findAllByOrderByIdDesc();
        }

        List<PeliculaShortDTO> peliculaShortDTOS = new ArrayList<>();

        for(Pelicula p : peliculas){
            PeliculaShortDTO peliculaShortDto = modelMapper.map(p, PeliculaShortDTO.class);
            peliculaShortDTOS.add(peliculaShortDto);
        }
        return peliculaShortDTOS;
    }

    @Override
    public PeliculaDTO getPeliculaDetailsById(Long idMovie) {

        Pelicula peliculaDB =  peliculaRepository.findById(idMovie)
                .orElseThrow(() -> new NotFoundException("Pelicula no encontrada"));

        return modelMapper.map(peliculaDB, PeliculaDTO.class);
    }

    @Override
    public List<PeliculaDTO> getAllPeliculaDetails() {

        List<PeliculaDTO> peliculas = peliculaRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, PeliculaDTO.class))
                .collect(Collectors.toList());

        return peliculas;
    }


    @Override
    public List<PeliculaShortDTO> getAllPeliculas() {
        List<Pelicula> peliculas = peliculaRepository.findAll();
        List<PeliculaShortDTO> peliculaShortDTOList = new ArrayList<>();
        peliculas.stream().forEach( (p) -> {
            PeliculaShortDTO pelicula = modelMapper.map(p, PeliculaShortDTO.class);
            peliculaShortDTOList.add(pelicula);
        });
        return peliculaShortDTOList;
    }

    @Override
    public PeliculaDTO addPersonajeToPelicula(Long idMovie, Long idPersonaje) {
        Pelicula peliculaDB = peliculaRepository.findById(idMovie)
                .orElseThrow(()-> new NotFoundException("Pelicula no encontrada"));

        Personaje personajeDB = personajeRepository.findById(idPersonaje)
                .orElseThrow(()-> new NotFoundException("Personaje no encontrado"));

        peliculaDB.getPersonajes().add(personajeDB);

        return modelMapper.map(peliculaRepository.save(peliculaDB), PeliculaDTO.class);
    }

    @Override
    public PeliculaDTO deletePersonajeFromPelicula(Long idMovie, Long idPersonaje) {

        Pelicula peliculaDB = peliculaRepository.findById(idMovie)
                .orElseThrow(()-> new NotFoundException("Pelicula no encontrada"));

        Personaje personajeDB = personajeRepository.findById(idPersonaje)
                .orElseThrow(() -> new NotFoundException("Personaje no encontrado"));

        peliculaDB.getPersonajes().remove(personajeDB);


        return modelMapper.map(peliculaRepository.save(peliculaDB), PeliculaDTO.class);
    }

    @Override
    public PeliculaDTO createPelicula(PeliculaDTO pelicula) {
        /**
         * @Param pelicula
         * Se consulta a la base de datos sobre la existencia del recurso - Idempotencia
         * @Return la entidad existente en la base de datos
         */
        Pelicula peliculaDB = peliculaRepository.findByGeneroAndTituloContainingIgnoreCase
                                                (pelicula.getGenero(), pelicula.getTitulo());
        if (peliculaDB != null){
            return modelMapper.map(peliculaDB, PeliculaDTO.class);
        }

        pelicula.setFecha_creacion(LocalDate.now());


        List<Long> idsPersonajes = new ArrayList<>();

        pelicula.getPersonajes().forEach(p -> {
            Long id = p.getId();
            idsPersonajes.add(id);
        });
        Pelicula peliculaSave = Pelicula.builder()
                                .imagen(pelicula.getImagen())
                                .titulo(pelicula.getTitulo())
                                .fecha_creacion(LocalDate.now())
                                .personajes(personajeRepository.findAllById(idsPersonajes))
                                .genero(generoRepository.findById(pelicula.getGenero().getId()).get())
                                .build();


        PeliculaDTO peliculaResponse =
                modelMapper.map(peliculaRepository.save(peliculaSave), PeliculaDTO.class);

        return peliculaResponse;
    }

    @Override
    public PeliculaDTO createPelicula(PeliculaDTO pelicula, Long idGenre) {
        /**
         * @Param pelicula
         * Se consulta a la base de datos sobre la existencia del recurso - Idempotencia
         * @Return la entidad existente en la base de datos
         */
        Genero generoDB = generoRepository.findById(idGenre)
                .orElseThrow(() -> new NotFoundException("Género no encontrado"));


        Pelicula peliculaDB = peliculaRepository
                .findByGeneroAndTituloContainingIgnoreCase(generoDB, pelicula.getTitulo());

        if (peliculaDB != null){
            return modelMapper.map(peliculaDB, PeliculaDTO.class);
        }

        pelicula.setFecha_creacion(LocalDate.now());


        List<Long> ids = new ArrayList<>();

        pelicula.getPersonajes().forEach(p -> {
            Long id = p.getId();
            ids.add(id);

        });
        Pelicula peliculaSave = Pelicula.builder()
                .imagen(pelicula.getImagen())
                .titulo(pelicula.getTitulo())
                .fecha_creacion(LocalDate.now())
                .personajes(personajeRepository.findAllById(ids))
                .genero(generoDB)
                .build();


        PeliculaDTO peliculaResponse =
                modelMapper.map(peliculaRepository.save(peliculaSave), PeliculaDTO.class);

        return peliculaResponse;

    }

    @Override
    public PeliculaDTO putPelicula(Long idPelicula, PeliculaDTO pelicula) {
        Pelicula peliculaDB = peliculaRepository.findById(idPelicula)
                                                .orElseThrow(() -> new NotFoundException("Pelicula no encontrada"));
        Genero generoDB = generoRepository.findById(pelicula.getGenero().getId())
                                                .orElseThrow(() -> new NotFoundException("Género no encontrado"));


        pelicula.setId(idPelicula);

        Pelicula peliculaSave = modelMapper.map(pelicula, Pelicula.class);

        peliculaSave.setFecha_creacion(peliculaDB.getFecha_creacion());

        peliculaSave.setPersonajes(peliculaDB.getPersonajes());

        peliculaSave.setGenero(generoDB);


        return modelMapper.map(peliculaRepository.save(peliculaSave), PeliculaDTO.class);
    }

    @Override
    public PeliculaDTO patchPelicula(Long idPelicula, Map<String, ?> pelicula) {
        Pelicula peliculaDB = peliculaRepository.findById(idPelicula)
                                                .orElseThrow(() -> new NotFoundException("Pelicula no encontrada"));


        Genero generoDB = new Genero();

        for (Map.Entry<String, ?> entry : pelicula.entrySet() ){
            if(entry.getKey().equalsIgnoreCase("id_genre")){
                generoDB = generoRepository.findById(Long.valueOf(String.valueOf(entry.getValue())))
                        .orElseThrow(()->new NotFoundException("Genero no encontrado"));
                peliculaDB.setGenero(generoDB);
            }

        }

        pelicula.forEach((k, v) -> {
            if (k != "id_genre"){
                Field field = ReflectionUtils.findField(Pelicula.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, peliculaDB, v);
            }

        });


        return modelMapper.map(peliculaRepository.save(peliculaDB), PeliculaDTO.class);
    }

    @Override
    public Void deletePelicula(Long id) {
        Pelicula peliculaDB = peliculaRepository.findById(id)
                                                .orElseThrow(() -> new NotFoundException("Pelicula no encontrada"));
        peliculaRepository.delete(peliculaDB);
        return null;
    }


}
