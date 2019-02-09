package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.Role;

import java.util.Set;

public interface RoleRepository {
    Set<Role> findAll();
}
