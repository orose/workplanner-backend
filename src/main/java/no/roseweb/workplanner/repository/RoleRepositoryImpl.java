package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleRepositoryImpl implements RoleRepository {
    @Override
    public Set<Role> findAll() {
        return null;
    }
}
