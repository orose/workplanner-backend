package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Invite;

public interface InviteRepository {
    Invite add(Invite invite);
    void delete(Invite invite);
    Invite findByEmail(String email);
}
