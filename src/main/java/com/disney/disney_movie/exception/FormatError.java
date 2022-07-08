package com.disney.disney_movie.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class FormatError {
    private List<Map<String, String>> messages;

    public static String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String, String> error = new HashMap<>();

                    error.put(err.getField(), err.getDefaultMessage());

                    return error;
                }).collect(Collectors.toList());
        FormatError errorMessage = FormatError.builder()
                .messages(errors).build();
        String jsonString = "";
        jsonString = String.valueOf(errorMessage.getMessages());

        return jsonString;
    }

    @Override
    public String toString() {
        return "FormatError{" +
                "messages=" + messages +
                '}';
    }
}
