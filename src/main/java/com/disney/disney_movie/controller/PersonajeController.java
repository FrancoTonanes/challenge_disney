package com.disney.disney_movie.controller;

import com.disney.disney_movie.dto.PersonajeDTO;
import com.disney.disney_movie.dto.PersonajeShortDTO;
import com.disney.disney_movie.exception.BadRequestException;
import com.disney.disney_movie.exception.FormatError;
import com.disney.disney_movie.service.interfaces.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(PersonajeController.URL)
public class PersonajeController {
    public static final String URL = "/characters";
    @Autowired
    private PersonajeService personajeService;


    @GetMapping
    public ResponseEntity<?> getPersonaje(@RequestParam (required = false) String name,
                                          @RequestParam (required = false) String edad,
                                          @RequestParam (required = false) String idMovie) {

        if(name != null){
            List<PersonajeShortDTO> personajeBD = personajeService.getPersonajeByName(name);
            return (personajeBD != null) ? ResponseEntity.ok(personajeBD) : ResponseEntity.notFound().build();
        }

        else if (edad != null){
            List<PersonajeShortDTO> personajeBD = personajeService.getPersonajeByEdad(edad);

            return (personajeBD != null) ? ResponseEntity.ok(personajeBD) : ResponseEntity.notFound().build();
        }
        else if (idMovie != null) {
            List<PersonajeShortDTO> personajeShortDTOS = personajeService.getPersonajesByMovie(idMovie);

            return (personajeShortDTOS != null) ? ResponseEntity.ok(personajeShortDTOS) : ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personajeService.getAllPersonajes());

    }

    @PostMapping
    public ResponseEntity<?> postPersonaje(@Valid @RequestBody PersonajeDTO personaje, BindingResult result){
        if (result.hasErrors()){
            throw new BadRequestException(FormatError.formatMessage(result));
        }
        return new ResponseEntity<>(personajeService.postPersonaje(personaje), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> patchPersonaje(@RequestParam  Long id,
                                            @RequestBody Map<String, ?> personaje){

        PersonajeDTO personajeDto = personajeService.patchPersonaje(id, personaje);

        return ResponseEntity.ok(personajeDto);
    }

    @PutMapping
    public ResponseEntity<?> putPersonaje(  @RequestParam  Long id,
                                            @Valid @RequestBody PersonajeDTO personaje,
                                            BindingResult result){
        if (result.hasErrors()){
            throw new BadRequestException(FormatError.formatMessage(result));
        }
        PersonajeDTO personajeDB = personajeService.putPersonaje(id, personaje);


        return ResponseEntity.ok(personajeDB);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePersonaje (@RequestParam Long id){

        return new ResponseEntity<>(personajeService.deletePersonaje(id), HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getPersonajeDetails(@RequestParam (required = false) Long idCharacter){

        return (idCharacter != null) ?
                ResponseEntity.ok(personajeService.getPersonajeDetailById(idCharacter)) :
                ResponseEntity.ok(personajeService.getAllPersonajesDetail());
    }


}
