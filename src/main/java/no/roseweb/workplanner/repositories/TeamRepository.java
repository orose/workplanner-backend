package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Team;

public interface TeamRepository {
    Team create(Team team);
    void delete(Team team);
    Team findById(Long id);
}
