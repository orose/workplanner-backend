package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.Invite;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface InviteService {
    Invite create(Invite invite);

    @PreAuthorize("hasPermission(#email, 'Invite', 'edit')")
    Integer delete(String email);

    @PreAuthorize("hasPermission(#email, 'Invite', 'read')")
    Invite findByEmail(String email);

    List<Invite> findAllByOrganizationId(Long organizationId, Integer offset, Integer limit);
    Integer countAll(Long organizationId);
}
