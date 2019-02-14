package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.User;

public interface UserRepository {
    void add(User user);
    User findByEmail(String email);
}
