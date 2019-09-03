package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = RestPath.API + RestPath.USERINFO)
    public ApplicationUser getUserinfo(HttpServletResponse response, Principal principal) {

        ApplicationUser user = userService.findByEmail(principal.getName());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        user.setPassword(null);

        response.setStatus(HttpServletResponse.SC_OK);

        return user;
    }
}
