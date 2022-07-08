package com.disney.disney_movie.exception;

import lombok.Builder;
import lombok.Data;


public class BadRequestException extends RuntimeException {

    private static final String DESCRIPTION = "Bad Request Exception (400)";

    public BadRequestException(String detail){
        super(DESCRIPTION + ". " + detail);
    }
}
