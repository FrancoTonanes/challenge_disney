package com.disney.disney_movie.controller;

import com.disney.disney_movie.dto.GeneroDTO;
import com.disney.disney_movie.exception.BadRequestException;
import com.disney.disney_movie.exception.FormatError;
import com.disney.disney_movie.service.interfaces.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(GeneroController.URL)
public class GeneroController {
    public final static String URL = "/genre";

    @Autowired
    GeneroService generoService;

    @GetMapping
    public ResponseEntity<?> getGenero (@RequestParam (required = false, name = "id")  Long idGenre){

        return (idGenre != null) ?
                ResponseEntity.ok(generoService.getGeneroById(idGenre))
                : ResponseEntity.ok(generoService.getAllGenero());
    }

    @PostMapping
    public ResponseEntity<?> postGenero (@Valid @RequestBody GeneroDTO genero, BindingResult result){
        if (result.hasErrors()){
            throw new BadRequestException(FormatError.formatMessage(result));
        }
        return new ResponseEntity<>(generoService.postGenero(genero), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> putGenero(@RequestParam Long id,
                                       @Valid @RequestBody GeneroDTO genero,
                                       BindingResult result){

        if (result.hasErrors()){
            throw new BadRequestException(FormatError.formatMessage(result));
        }
        return new ResponseEntity<>(generoService.putGenero(id, genero), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteGenero(@RequestParam Long id){
        return new ResponseEntity<>(generoService.deleteGenero(id), HttpStatus.OK);
    }
}
