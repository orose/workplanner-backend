package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.User;

public interface UserService {
    User add(User user);
    User findByEmail(String email);
}
