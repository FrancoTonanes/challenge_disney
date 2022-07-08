package com.disney.disney_movie.controller;

import com.disney.disney_movie.dto.PeliculaDTO;
import com.disney.disney_movie.dto.PeliculaShortDTO;
import com.disney.disney_movie.exception.BadRequestException;
import com.disney.disney_movie.exception.FormatError;
import com.disney.disney_movie.service.interfaces.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(PeliculaController.URL)
public class PeliculaController {
    public final static String URL = "/movies";
    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<?> getPeliculas(@RequestParam (required = false) String name,
                                          @RequestParam (required = false) String genre,
                                          @RequestParam (required = false) String order) {

        if(name != null){
            List<PeliculaShortDTO> peliculasBD = peliculaService.getPeliculaByTitulo(name);

            return ((peliculasBD != null) ? ResponseEntity.ok(peliculasBD) : ResponseEntity.notFound().build());
        }

        else if (genre != null){
            List<PeliculaShortDTO> peliculasBD = peliculaService.getPeliculaByGenero(genre);

            return (peliculasBD != null) ? ResponseEntity.ok(peliculasBD) : ResponseEntity.notFound().build();

        } else if (order != null) {

            List<PeliculaShortDTO> peliculasBD = peliculaService.getPeliculaByOrden(order);

            return (peliculasBD != null) ? ResponseEntity.ok(peliculasBD) : ResponseEntity.notFound().build();



        }

        return ResponseEntity.ok(peliculaService.getAllPeliculas());

    }

    @PostMapping
    public ResponseEntity<?> postPelicula(@RequestParam (required = false) Long idGenre,
                                          @Valid @RequestBody PeliculaDTO pelicula, BindingResult result){

        if (result.hasErrors()){
            throw new BadRequestException(FormatError.formatMessage(result));
        }
        if (idGenre != null){

            PeliculaDTO peliculaDto = peliculaService.createPelicula(pelicula, idGenre);

            return new ResponseEntity<>(peliculaDto, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(peliculaService.createPelicula(pelicula), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> putPelicula(@RequestParam Long idMovie,
                                         @Valid @RequestBody PeliculaDTO pelicula,
                                         BindingResult result){
        if (result.hasErrors()){
            throw new BadRequestException(FormatError.formatMessage(result));
        }

        return new ResponseEntity<>(peliculaService.putPelicula(idMovie, pelicula), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> patchPelicula(@RequestParam Long idMovie,
                                           @RequestBody Map<String, ?> pelicula){

        return new ResponseEntity<>(peliculaService.patchPelicula(idMovie, pelicula), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePelicula(@RequestParam Long idMovie){
        return ResponseEntity.ok(peliculaService.deletePelicula(idMovie));
    }

    @PostMapping("/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<?> addPersonajeToPelicula(@PathVariable Long idMovie,
                                                    @PathVariable Long idCharacter){
        return new ResponseEntity<>
                (peliculaService.addPersonajeToPelicula(idMovie, idCharacter), HttpStatus.OK);
    }

    @DeleteMapping("/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<?> deletePersonajeFromPelicula(@PathVariable Long idMovie,
                                                         @PathVariable Long idCharacter){
        return new ResponseEntity<>
                (peliculaService.deletePersonajeFromPelicula(idMovie, idCharacter), HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getDetailsPeliculas(@RequestParam (required = false) Long idMovie){

        return (idMovie != null) ?
                ResponseEntity.ok(peliculaService.getPeliculaDetailsById(idMovie)) :
                ResponseEntity.ok(peliculaService.getAllPeliculaDetails());
    }



}
