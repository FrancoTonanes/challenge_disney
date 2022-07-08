package com.disney.disney_movie.dto;

import com.disney.disney_movie.entity.Pelicula;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GeneroDTO implements Serializable {
    private Long id;
    @NotBlank(message = "Debe incluir un nombre al género")
    @Size(min = 3, max = 30, message = "Debe tener un mínimo de 3 caracteres y un máximo de 30")
    private String nombre;

    private String imagen;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "personajes", "genero", "fecha_creacion" })
    List<Pelicula> peliculas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public GeneroDTO(Long id, String nombre, String imagen, List<Pelicula> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.peliculas = peliculas;
    }

    public GeneroDTO() {
    }

    @Override
    public String toString() {
        return "GeneroDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", peliculas=" + peliculas +
                '}';
    }
}
