package com.disney.disney_movie.dto;

import com.disney.disney_movie.entity.Pelicula;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonajeDTO implements Serializable {

    private Long id;

    private String imagen;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Positive(message = "La edad debe ser mayor que cero")
    private Integer edad;
    @Positive(message = "El peso debe ser mayor que cero")
    private Double peso;

    @NotBlank(message = "La historia no puede estar vacío")
    private String historia;
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "personajes", "genero" })
    private List<Pelicula> peliculas = new ArrayList<>();

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonajeDTO(String imagen, String nombre, Integer edad, Double peso, String historia, List<Pelicula> peliculas) {

        this.imagen = imagen;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        this.peliculas = peliculas;
    }
    public PersonajeDTO(){

    }

    @Override
    public String toString() {
        return "PersonajeDto{" +
                "id=" + id +
                ", imagen='" + imagen + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", peso=" + peso +
                ", historia='" + historia + '\'' +
                ", peliculas=" + peliculas +
                '}';
    }
}
