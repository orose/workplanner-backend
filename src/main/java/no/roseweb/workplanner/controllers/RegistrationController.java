package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/register")
public class RegistrationController {
    @Autowired
    private UserService userService;

    RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "")
    public User registerUser(@RequestBody User user, HttpServletResponse response) {
        userService.add(user);

        response.setStatus(HttpServletResponse.SC_CREATED);

        return user;
    }
}