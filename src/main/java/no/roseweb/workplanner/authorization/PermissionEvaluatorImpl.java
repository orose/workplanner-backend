package no.roseweb.workplanner.authorization;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.repositories.InviteRepository;
import no.roseweb.workplanner.repositories.OrganizationRepository;
import no.roseweb.workplanner.repositories.WorkorderRepository;
import no.roseweb.workplanner.services.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PermissionEvaluatorImpl extends PermissionEvaluatorBase implements PermissionEvaluator {

    private final WorkorderRepository workorderRepository;
    private final OrganizationRepository organizationRepository;
    private final InviteRepository inviteRepository;

    private static final String PERMISSION_EDIT = "edit";
    private static final String PERMISSION_READ = "read";

    private static final String WORKORDER = "Workorder";
    private static final String INVITE = "Invite";
    private static final String ORGANIZATION = "Organization";

    public PermissionEvaluatorImpl(
            UserService userService,
            WorkorderRepository workorderRepository,
            OrganizationRepository organizationRepository,
            InviteRepository inviteRepository) {
        super(userService);
        this.workorderRepository = workorderRepository;
        this.organizationRepository = organizationRepository;
        this.inviteRepository = inviteRepository;
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
        } else if (targetDomainObject instanceof Organization) {
            if (permission.equals(PERMISSION_EDIT) || permission.equals(PERMISSION_READ)) {
                Organization object = (Organization) targetDomainObject;
                return user.getOrganizationId().equals(object.getId());
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        ApplicationUser user = getUserFromAuth(auth);

        if (PERMISSION_READ.equals(permission)) {
            switch (targetType) {
                case INVITE:
                    return evaluateInviteRead(user, (String) targetId);
                case WORKORDER:
                    return evaluateWorkorderRead(user, (Long) targetId);
                case ORGANIZATION:
                    return evaluateOrganizationRead(user, (Long) targetId);
                default:
                    return false;
            }
        } else if (PERMISSION_EDIT.equals(permission)) {
            if (INVITE.equals(targetType)) {
                return evaluateInviteEdit(user, (String) targetId);
            }
            return false;
        }
        return false;
    }

    private boolean evaluateOrganizationRead(ApplicationUser user, Long id) {
        if (user == null) {
            return false;
        }
        Organization organization = organizationRepository.findById(id);
        return organization != null
            && user.getOrganizationId().equals(organization.getId());
    }

    private Boolean evaluateWorkorderRead(ApplicationUser user, Long id) {
        if (user == null) {
            return false;
        }
        Workorder workorder = workorderRepository.findById(id);
        return workorder != null
            && user.getOrganizationId().equals(workorder.getOrganizationId());
    }

    private Boolean evaluateInviteRead(ApplicationUser user, String email) {
        if (user == null) {
            return false;
        }
        Invite invite = inviteRepository.findByEmail(email);
        return invite != null
                && user.getOrganizationId().equals(invite.getOrganizationId());
    }

    private Boolean evaluateInviteEdit(ApplicationUser user, String email) {
        return evaluateInviteRead(user, email);
    }
}
