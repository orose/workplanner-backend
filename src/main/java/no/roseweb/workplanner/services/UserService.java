package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.responses.UserListResponse;

public interface UserService {
    ApplicationUser create(ApplicationUser user);
    ApplicationUser findByEmail(String email);

    UserListResponse findByOrganizationId(Long organizationId, Integer offset, Integer limit);

    int connectToTeam(Long userId, Long teamId, String permissionKey);
    void disconnectFromTeam(Long userId, Long teamId);
}
