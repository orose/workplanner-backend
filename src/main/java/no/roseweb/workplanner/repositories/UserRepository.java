package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.ApplicationUser;

import java.util.List;

public interface UserRepository {
    ApplicationUser create(ApplicationUser user);
    ApplicationUser findByEmail(String email);
    ApplicationUser findById(Long id);
    List<ApplicationUser> findByOrganizationId(Long organizationId, Integer offset, Integer limit);
    Integer countAllByOrganizationId(Long organizationId);
}
