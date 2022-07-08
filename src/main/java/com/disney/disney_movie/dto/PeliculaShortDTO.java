package com.disney.disney_movie.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class PeliculaShortDTO implements Serializable {

    private String imagen;

    private String titulo;

    private LocalDate fecha_creacion;



    public PeliculaShortDTO(String imagen, String titulo, LocalDate fecha_creacion) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.fecha_creacion = fecha_creacion;
    }

    public PeliculaShortDTO() {
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
}
