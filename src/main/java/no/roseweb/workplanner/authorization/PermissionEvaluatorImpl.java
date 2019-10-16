package no.roseweb.workplanner.authorization;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.services.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PermissionEvaluatorImpl implements PermissionEvaluator {

    private UserService userService;

    public PermissionEvaluatorImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        ApplicationUser user = getUserFromAuth(auth);

        if (targetDomainObject == null) {
            return true;
        }

        if (targetDomainObject instanceof Workorder) {
            if (permission.equals("edit")) {
                Workorder workorder = (Workorder) targetDomainObject;
                return workorder.getTeamId().equals(user.getOrganizationId());
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        throw new UnsupportedOperationException("hasPermission() by ID is not supported");
    }

    private ApplicationUser getUserFromAuth(Authentication auth) {
        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }
        String username = (String) auth.getPrincipal();
        return userService.findByEmail(username);
    }
}
