package com.disney.disney_movie.dto;

import com.disney.disney_movie.entity.Genero;
import com.disney.disney_movie.entity.Personaje;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDTO implements Serializable {

    private Long id;


    private String imagen;

    @NotBlank(message = "El titulo no puede estar vacío")
    @Size(min = 3, max = 75, message = "El titulo debe tener minimo 3 caracteres y un máximo de 75")
    private String titulo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha_creacion;

    //@NotBlank(message = "Debe incluir los personajes asociados")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "peliculas" })
    private List<Personaje> personajes = new ArrayList<>();

    //@NotBlank(message = "Debe incluir el género")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "peliculas" })
    private Genero genero;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public PeliculaDTO(String imagen, String titulo, LocalDate fecha_creacion, List<Personaje> personajes, Genero genero) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.fecha_creacion = fecha_creacion;
        this.personajes = personajes;
        this.genero = genero;
    }

    public PeliculaDTO() {
    }

    @Override
    public String toString() {
        return "PeliculaDto{" +
                "id=" + id +
                ", imagen='" + imagen + '\'' +
                ", titulo='" + titulo + '\'' +
                ", fecha_creacion=" + fecha_creacion +
                ", personajes=" + personajes +
                ", genero=" + genero +
                '}';
    }
}
