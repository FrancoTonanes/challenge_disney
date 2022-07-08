package com.disney.disney_movie.dto;

import java.io.Serializable;

public class PersonajeShortDTO implements Serializable {

    private String imagen;
    private String nombre;

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

    public PersonajeShortDTO(String imagen, String nombre) {
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public PersonajeShortDTO() {
    }
}
