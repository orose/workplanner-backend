package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.User;

public interface UserService {
    User create(User user);
    User findByEmail(String email);
}
