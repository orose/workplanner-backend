package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Organization;

import java.util.List;

public interface OrganizationRepository {
    Organization create(Organization organization);
    List<Organization> findAllOrderedByName();
    Organization findById(Long id);
}
