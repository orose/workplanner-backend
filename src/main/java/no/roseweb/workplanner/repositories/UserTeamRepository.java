package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.UserTeam;

public interface UserTeamRepository {
    int create(UserTeam team);
    int remove(String userId, Long teamId);
}
