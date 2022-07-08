package com.disney.disney_movie.controller;


import com.disney.disney_movie.dto.UserRoleDTO;
import com.disney.disney_movie.dto.UserRegisterDTO;
import com.disney.disney_movie.entity.Role;
import com.disney.disney_movie.entity.User;
import com.disney.disney_movie.exception.BadRequestException;
import com.disney.disney_movie.exception.FormatError;
import com.disney.disney_movie.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/users") //SOLO ADMIN
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()){
            throw new BadRequestException(FormatError.formatMessage(result));
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @PostMapping("auth/register") //All users
    public ResponseEntity<UserRegisterDTO> saveUserCommon(@Valid @RequestBody User user, BindingResult result) throws IOException {

        if (result.hasErrors()){
            throw new BadRequestException(FormatError.formatMessage(result));
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("auth/register").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUserCommon(user));
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/roles").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/users/{userId}/roles")
    public ResponseEntity<Role> saveUser(@PathVariable Long userId, @RequestBody UserRoleDTO userRoleDTO) {
        userService.addRoleToUser(userRoleDTO.getUsername(), userRoleDTO.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);

    }
}
