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
    private static final String PERMISSION_EDIT = "edit";
    private static final String PERMISSION_READ = "read";

    private static final String WORKORDER = "Workorder";

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
            if (permission.equals(PERMISSION_EDIT) || permission.equals(PERMISSION_READ)) {
                Workorder object = (Workorder) targetDomainObject;
                Workorder workorder = workorderRepository.findById(object.getId());
                return user.getOrganizationId().equals(workorder.getOrganizationId());
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        ApplicationUser user = getUserFromAuth(auth);

        if (PERMISSION_READ.equals(permission)) {
            if (WORKORDER.equals(targetType)) {
                return evaluateWorkorderRead(user, (Long) targetId);
            }
        }
        return false;
    }

    private Boolean evaluateWorkorderRead(ApplicationUser user, Long id) {
        Workorder workorder = workorderRepository.findById(id);
        return user.getOrganizationId().equals(workorder.getOrganizationId());
    }
}
