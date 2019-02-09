package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.User;

public interface UserRepository {
    void add(User user);
    User findByEmail(String email);
}
