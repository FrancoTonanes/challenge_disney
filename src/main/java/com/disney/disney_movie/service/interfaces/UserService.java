package com.disney.disney_movie.service.interfaces;




import com.disney.disney_movie.dto.UserRegisterDTO;
import com.disney.disney_movie.entity.Role;
import com.disney.disney_movie.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    UserRegisterDTO saveUserCommon(User user) throws IOException;
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    User getUser(String username);
    List<User> getUsers();
}
