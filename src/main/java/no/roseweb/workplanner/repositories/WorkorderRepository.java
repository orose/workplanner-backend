package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;

import java.util.List;

public interface WorkorderRepository {
    Workorder create(WorkorderCreateRequest request, ApplicationUser user);
    Workorder update(Workorder workorder, ApplicationUser user);
    Workorder findById(Long id);
    List<Workorder> getAll(Long organizationId, Integer limit, Integer offset);
    Integer countAll(Long organizationId);
}
