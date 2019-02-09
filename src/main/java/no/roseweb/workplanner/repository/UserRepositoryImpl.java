package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
