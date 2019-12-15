package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;

public interface UserService {
    ApplicationUser create(ApplicationUser user);
    ApplicationUser findByEmail(String email);
    int connectToTeam(String email, Long teamId, String permissionKey);
    void disconnectFromTeam(String email, Long teamId);
}
