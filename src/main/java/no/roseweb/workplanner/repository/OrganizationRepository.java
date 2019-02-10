package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.Organization;

import java.util.List;

public interface OrganizationRepository {
    Organization add(Organization organization);
    List<Organization> findAllOrderedByName();
    Organization findById(Long id);
}
