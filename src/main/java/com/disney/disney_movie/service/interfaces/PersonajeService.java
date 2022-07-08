package com.disney.disney_movie.service.interfaces;

import com.disney.disney_movie.dto.PersonajeDTO;
import com.disney.disney_movie.dto.PersonajeShortDTO;

import java.util.List;
import java.util.Map;

public interface PersonajeService{
    List<PersonajeShortDTO> getPersonajeByName(String name);

    List<PersonajeShortDTO> getPersonajeByEdad(String edad);
    List<PersonajeShortDTO> getPersonajesByMovie(String idMovie);
    List<PersonajeShortDTO> getAllPersonajes();

    PersonajeDTO getPersonajeDetailById(Long id);
    List<PersonajeDTO> getAllPersonajesDetail();

    PersonajeDTO postPersonaje(PersonajeDTO personaje);

    PersonajeDTO patchPersonaje(Long id, Map<String, ?> personaje);

    PersonajeDTO putPersonaje(Long id, PersonajeDTO personaje);

    Void deletePersonaje(Long id);

}
