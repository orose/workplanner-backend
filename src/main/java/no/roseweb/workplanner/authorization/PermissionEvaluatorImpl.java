package no.roseweb.workplanner.authorization;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.repositories.WorkorderRepository;
import no.roseweb.workplanner.services.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PermissionEvaluatorImpl extends PermissionEvaluatorBase implements PermissionEvaluator {

    private WorkorderRepository workorderRepository;

    public PermissionEvaluatorImpl(UserService userService, WorkorderRepository workorderRepository) {
        super(userService);
        this.workorderRepository = workorderRepository;
    }

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        ApplicationUser user = getUserFromAuth(auth);

        if (targetDomainObject == null) {
            return true;
        }

        if (targetDomainObject instanceof Workorder) {
            if (permission.equals("edit")) {
                Workorder object = (Workorder) targetDomainObject;
                Workorder workorder = workorderRepository.findById(object.getId());
                return user.getOrganizationId().equals(workorder.getOrganizationId());
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        throw new UnsupportedOperationException("hasPermission() by ID is not supported");
    }
}
