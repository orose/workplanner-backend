package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RegistrationController {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);
    private UserService userService;

    RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = RestPath.REGISTER)
    public ApplicationUser registerUser(@RequestBody ApplicationUser user, HttpServletResponse response) {
        LOG.info("Register user. Email={}, OrganizationId={}", user.getEmail(), user.getOrganizationId());
        ApplicationUser createdUser = userService.create(user);

        response.setStatus(HttpServletResponse.SC_CREATED);

        LOG.info("User registered successfully. Email={}, OrganizationId={}",
                createdUser.getEmail(), createdUser.getOrganizationId());
        return createdUser;
    }
}
