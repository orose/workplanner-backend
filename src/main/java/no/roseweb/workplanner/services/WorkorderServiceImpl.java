package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;
import no.roseweb.workplanner.repositories.UserWorkorderRepository;
import no.roseweb.workplanner.repositories.WorkorderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkorderServiceImpl implements WorkorderService {

    private WorkorderRepository workorderRepository;
    private UserWorkorderRepository userWorkorderRepository;

    public WorkorderServiceImpl(
        WorkorderRepository workorderRepository,
        UserWorkorderRepository userWorkorderRepository
    ) {
        this.workorderRepository = workorderRepository;
        this.userWorkorderRepository = userWorkorderRepository;
    }

    @Override
    public Workorder create(WorkorderCreateRequest request, ApplicationUser user) {
        return workorderRepository.create(request, user);
    }

    @Override
    public Workorder update(Workorder workorder, ApplicationUser user) {
        return workorderRepository.update(workorder, user);
    }

    @Override
    public Workorder findById(Long id) {
        return workorderRepository.findById(id);
    }

    @Override
    public List<Workorder> getAll(Long organizationId, Integer limit, Integer offset) {
        return workorderRepository.getAll(organizationId, limit, offset);
    }

    @Override
    public Integer countAll(Long organizationId) {
        return workorderRepository.countAll(organizationId);
    }

    @Override
    public Integer assignUser(Workorder workorder, String userId) {
        return userWorkorderRepository.addAssignment(userId, workorder.getId());
    }

    @Override
    public Integer unassignUser(Workorder workorder, String userId) {
        return userWorkorderRepository.removeAssignment(userId, workorder.getId());
    }
}
