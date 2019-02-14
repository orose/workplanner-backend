package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.User;

public interface UserRepository {
    User add(User user);
    User findByEmail(String email);
}
