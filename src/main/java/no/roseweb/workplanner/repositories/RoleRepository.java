package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Role;

import java.util.List;

public interface RoleRepository {
    List<Role> findAllOrderedByName();
    Role findById(Long id);
    Role create(Role role);
}
