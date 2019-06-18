package no.roseweb.workplanner.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.services.UserService;

@RestController
public class RegistrationController {
    private UserService userService;

    RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = RestPath.REGISTER)
    public User registerUser(@RequestBody User user, HttpServletResponse response) {
        User createdUser = userService.create(user);

        response.setStatus(HttpServletResponse.SC_CREATED);

        return createdUser;
    }
}
