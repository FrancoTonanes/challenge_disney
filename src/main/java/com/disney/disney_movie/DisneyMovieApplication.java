package com.disney.disney_movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class DisneyMovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisneyMovieApplication.class, args);
	}

}
