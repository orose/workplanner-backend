package no.roseweb.workplanner.authorization;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class PermissionEvaluatorBase {

    private UserService userService;

    public PermissionEvaluatorBase(UserService userService) {
        this.userService = userService;
    }

    protected ApplicationUser getUserFromAuth(Authentication auth) {
        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }
        String username;
        if (auth.getPrincipal() instanceof User) {
            username = ((User) auth.getPrincipal()).getUsername();
        } else {
            username = (String) auth.getPrincipal();
        }
        return userService.findByEmail(username);
    }
}
