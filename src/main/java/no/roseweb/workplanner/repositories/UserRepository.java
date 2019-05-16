package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.User;

public interface UserRepository {
    User create(User user);
    User findByEmail(String email);
    User findById(Long id);
}
