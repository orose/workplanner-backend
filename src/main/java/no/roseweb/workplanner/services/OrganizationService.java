package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Organization;
import org.springframework.security.access.prepost.PreAuthorize;

public interface OrganizationService {
    Organization create(Organization organization, ApplicationUser user);

    @PreAuthorize("hasPermission(#id, 'Organization', 'read')")
    Organization findById(Long id);
}
