package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.models.UserTeam;

public interface UserService {
    User create(User user);
    User findByEmail(String email);
    UserTeam connectToTeam(String email, Long teamId, String permissionKey);
    void disconnectFromTeam(String email, Long teamId);
}
