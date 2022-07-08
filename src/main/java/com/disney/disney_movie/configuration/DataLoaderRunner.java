package com.disney.disney_movie.configuration;


import com.disney.disney_movie.entity.Role;
import com.disney.disney_movie.entity.User;
import com.disney.disney_movie.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * This runner has only been created to demonstrate and test the operation of loading the database
 * Like the .sql data
 */
@AllArgsConstructor
@Component
public class DataLoaderRunner implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.saveRole(new Role(null, "ROLE_USER"));
        userService.saveRole(new Role(null, "ROLE_MANAGER"));
        userService.saveRole(new Role(null, "ROLE_ADMIN"));

        userService.saveUser(new User(null, "Goku", "Goku", "goku@gmail.com", "1234", new ArrayList<>()));
        userService.saveUser(new User(null, "Vegeta", "Vegetta", "vegeta@gmail.com", "1234", new ArrayList<>()));
        userService.saveUser(new User(null, "Gohan", "Gohan", "gohan@hotmail.com", "1234", new ArrayList<>()));
        userService.saveUser(new User(null, "Trunks", "Trunks", "trunks@gmail.com", "1234", new ArrayList<>()));

        userService.addRoleToUser("Goku", "ROLE_USER");
        userService.addRoleToUser("Vegeta", "ROLE_USER");
        userService.addRoleToUser("Gohan", "ROLE_MANAGER");
        userService.addRoleToUser("Trunks", "ROLE_USER");
        userService.addRoleToUser("Trunks", "ROLE_MANAGER");
        userService.addRoleToUser("Trunks", "ROLE_ADMIN");
    }
}
