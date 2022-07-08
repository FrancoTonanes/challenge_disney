package com.disney.disney_movie.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pelicula")
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Pelicula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pelicula_id", unique = true, nullable = false)
    private Long id;

    private String imagen;
    @NotBlank(message = "El titulo no puede estar vacío")
    @Size(min = 3, max = 75, message = "El titulo debe tener minimo 3 caracteres y un máximo de 75")
    private String titulo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha_creacion;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "peliculas" })
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pelicula_personaje",
            joinColumns = @JoinColumn(name = "pelicula_id", referencedColumnName = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "personaje_id", referencedColumnName = "personaje_id"))

    private List<Personaje> personajes;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "peliculas" })
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "genero_id")
    @JsonBackReference
    private Genero genero;

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

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

    public void setPersonajes(List<Personaje> personajes_ascociados) {
        this.personajes = personajes_ascociados;
    }


    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", imagen='" + imagen + '\'' +
                ", titulo='" + titulo + '\'' +
                ", fecha_creacion=" + fecha_creacion +
                ", personajes=" + personajes +
                ", genero=" + genero +
                '}';
    }
}
