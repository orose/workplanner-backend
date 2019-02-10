package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.Role;

import java.util.List;

public interface RoleRepository {
    List<Role> findAllOrderedByName();
    Role findById(Long id);
    Role addRole(Role role);
}
