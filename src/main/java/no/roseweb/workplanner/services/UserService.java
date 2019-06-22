package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.UserTeam;

public interface UserService {
    ApplicationUser create(ApplicationUser user);
    ApplicationUser findByEmail(String email);
    UserTeam connectToTeam(String email, Long teamId, String permissionKey);
    void disconnectFromTeam(String email, Long teamId);
}
