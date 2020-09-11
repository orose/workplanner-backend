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
    List<Workorder> getAll(Long organizationId, Integer limit, Integer offset);
    Integer countAll(Long organizationId);

    @PreAuthorize("hasPermission(#workorder, 'edit')")
    Integer assignUser(Workorder workorder, Long userId);

    @PreAuthorize("hasPermission(#workorder, 'edit')")
    Integer unassignUser(Workorder workorder, Long userId);
}
