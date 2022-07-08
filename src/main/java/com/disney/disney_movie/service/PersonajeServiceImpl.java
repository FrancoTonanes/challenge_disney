package com.disney.disney_movie.service;

import com.disney.disney_movie.dto.PersonajeDTO;
import com.disney.disney_movie.dto.PersonajeShortDTO;
import com.disney.disney_movie.entity.Pelicula;
import com.disney.disney_movie.entity.Personaje;
import com.disney.disney_movie.exception.NotFoundException;
import com.disney.disney_movie.repository.PeliculaRepository;
import com.disney.disney_movie.repository.PersonajeRepository;
import com.disney.disney_movie.service.interfaces.PersonajeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;
    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PersonajeShortDTO> getPersonajeByName(String name) {

        List<Personaje> personajeDB = personajeRepository.findByNombreContainingIgnoreCase(name);

        List<PersonajeShortDTO> personajeShortDto = new ArrayList<>();

        personajeDB.parallelStream().forEach( ( p ) -> {
            PersonajeShortDTO personajeShortDTO1 = modelMapper.map(p, PersonajeShortDTO.class);
            personajeShortDto.add(personajeShortDTO1);
        });


        return personajeShortDto;
    }


    @Override
    public List<PersonajeShortDTO> getPersonajeByEdad(String edad) {

        List<Personaje> personajesDB = personajeRepository.findByEdad(Integer.parseInt(edad));
        if(personajesDB.isEmpty()){
            return null;
        }

        List<PersonajeShortDTO> personajesShortDtos = new ArrayList<>();

        personajesDB.parallelStream().forEach( (p) -> {
            PersonajeShortDTO personajeShortDTO1 = modelMapper.map(p, PersonajeShortDTO.class);
            personajesShortDtos.add(personajeShortDTO1);
        } );

        return personajesShortDtos;
    }

    @Override
    public List<PersonajeShortDTO> getPersonajesByMovie(String idMovie) {

        Pelicula peliculaDB = peliculaRepository.findById(Long.valueOf(idMovie)).orElse(null);

        if (peliculaDB == null){
            return null;
        }

        List<Personaje> personajes = peliculaDB.getPersonajes();
        List<PersonajeShortDTO> personajesDto = new ArrayList<>();

        personajes.parallelStream().forEach( p -> {
            PersonajeShortDTO personaje = modelMapper.map(p, PersonajeShortDTO.class);
            personajesDto.add(personaje);
        });

        return personajesDto;


    }

    @Override
    public List<PersonajeShortDTO> getAllPersonajes() {

        List<Personaje> personajesDB = personajeRepository.findAll();

        List<PersonajeShortDTO> personajeShortDTOS = new ArrayList<>();

        personajesDB.parallelStream().forEach( ( p ) -> {
            PersonajeShortDTO personajeShortDto = modelMapper.map(p, PersonajeShortDTO.class);
            personajeShortDTOS.add(personajeShortDto);
        });
        return personajeShortDTOS;
    }

    @Override
    public PersonajeDTO getPersonajeDetailById(Long id) {

        Personaje personajeBD = personajeRepository.findById(id)
                                        .orElseThrow(() -> new NotFoundException("Personaje no encontrado"));


        PersonajeDTO personaje = modelMapper.map(personajeBD, PersonajeDTO.class);

        return personaje;

    }

    @Override
    public List<PersonajeDTO> getAllPersonajesDetail() {
        List<PersonajeDTO> personajesDetail = personajeRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, PersonajeDTO.class)).collect(Collectors.toList());

        return personajesDetail;
    }

    @Override
    public PersonajeDTO postPersonaje(PersonajeDTO personaje) {

        /**
         * Se consulta a la base de datos si ya existe el actor para mantener la idempotencia del método
         * return personajeDto
         */

        Personaje personajeDB = personajeRepository.findByEdadAndNombreContainingIgnoreCase
                (personaje.getEdad(), personaje.getNombre());

        if (personajeDB != null){

            PersonajeDTO personajeDto = modelMapper.map(personajeDB, PersonajeDTO.class);

            return personajeDto;
        }
        Personaje personajeSave = personajeRepository.save(modelMapper.map(personaje, Personaje.class));

        PersonajeDTO personajeResponse =
                modelMapper.map(personajeSave, PersonajeDTO.class);


        return personajeResponse;
    }

    @Override
    public PersonajeDTO patchPersonaje(Long id, Map<String, ?> personaje) {
        Personaje personajeDB = personajeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Personaje no encontrado"));

        if (personajeDB == null) {
            return null;
        }

        personaje.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Personaje.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, personajeDB, v);

        });
        PersonajeDTO personajeResponse = modelMapper.map(personajeRepository.save(personajeDB), PersonajeDTO.class);

        return personajeResponse;

    }

    @Override
    public PersonajeDTO putPersonaje(Long id, PersonajeDTO personaje) {
        Personaje personajeDB = personajeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Personaje no encotrado"));

        personaje.setId(id);
        personaje.setPeliculas(personajeDB.getPeliculas());
        Personaje personajeSave = personajeRepository.save(modelMapper.map(personaje, Personaje.class));

        return modelMapper.map(personajeSave, PersonajeDTO.class);
    }

    @Override
    public Void deletePersonaje(Long id) {
        // personajeRepository.delete elimina entidades que no corresponde eliminar por id
        personajeRepository.deleteInBatch(personajeRepository.findAllById(Collections.singleton(id)));
        return null;
    }


}
