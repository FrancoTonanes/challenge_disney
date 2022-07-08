package com.disney.disney_movie.service.interfaces;

import java.io.IOException;

public interface EmailService {
    String sendEmail(String email, String user) throws IOException;
}
