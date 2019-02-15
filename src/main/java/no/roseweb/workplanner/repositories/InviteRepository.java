package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Invite;

public interface InviteRepository {
    Invite create(Invite invite);
    void delete(Invite invite);
    Invite findByEmail(String email);
}
