package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.User;

public interface UserRepository {
    void save(User user);
    User findByEmail(String email);
}
