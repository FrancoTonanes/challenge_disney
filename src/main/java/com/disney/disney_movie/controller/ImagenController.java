package com.disney.disney_movie.controller;

import com.disney.disney_movie.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImagenController {
    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/movie/{id}")
    public ResponseEntity<?> uploadInMovies(@RequestParam(value = "imagen") MultipartFile file,
                                            @PathVariable Long id) {

        return new ResponseEntity<>(imageService.updateMovie(file, id), HttpStatus.OK);
    }

    @PostMapping(value = "/character/{id}")
    public ResponseEntity<?> uploadInCharacters(@RequestParam(value = "imagen") MultipartFile file,
                                                @PathVariable Long id) {
        return new ResponseEntity<>(imageService.updateCharacter(file, id), HttpStatus.OK);
    }

    @PostMapping(value = "/genre/{id}")
    public ResponseEntity<?> uploadInGenders(@RequestParam(value = "imagen") MultipartFile file,
                                             @PathVariable Long id) {
        return new ResponseEntity<>(imageService.updateGender(file, id), HttpStatus.OK);
    }
}
