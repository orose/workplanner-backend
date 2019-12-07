package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface WorkorderService {
    Workorder create(WorkorderCreateRequest request, ApplicationUser user);

    @PreAuthorize("hasPermission(#workorder, 'edit')")
    Workorder update(Workorder workorder, ApplicationUser user);

    @PreAuthorize("hasPermission(#id, 'Workorder', 'read')")
    Workorder findById(Long id);
    List<Workorder> getAll(Integer limit, Integer offset);
    Integer countAll();
}
