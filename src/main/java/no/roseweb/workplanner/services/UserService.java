package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.User;

public interface UserService {
    void save(User user);
    User findByEmail(String email);
}
