package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.OrganizationUserListResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {
    ApplicationUser create(ApplicationUser user);
    ApplicationUser findByEmail(String email);

    @PreAuthorize("hasPermission(#organizationId, 'Organization', 'read')")
    OrganizationUserListResponse findByOrganizationId(Long organzationId, Integer offset, Integer limit);
    int connectToTeam(Long userId, Long teamId, String permissionKey);
    void disconnectFromTeam(Long userId, Long teamId);
}
