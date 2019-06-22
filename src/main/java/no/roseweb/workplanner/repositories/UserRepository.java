package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.ApplicationUser;

public interface UserRepository {
    ApplicationUser create(ApplicationUser user);
    ApplicationUser findByEmail(String email);
    ApplicationUser findById(Long id);
}
