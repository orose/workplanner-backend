package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleRepositoryImpl implements RoleRepository {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Set<Role> findAll() {
        return null;
    }
}
