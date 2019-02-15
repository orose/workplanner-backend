package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.UserTeam;

public interface UserTeamRepository {
    UserTeam create(UserTeam team);
    void remove(UserTeam userTeam);
    UserTeam findById(Long id);
}
