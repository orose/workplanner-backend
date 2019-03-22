package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Invite;

import java.util.List;

public interface InviteRepository {
    Invite create(Invite invite);
    Integer delete(String email);
    Invite findByEmail(String email);
    List<Invite> findAllByOrganizationId(Long organizationId);
}
