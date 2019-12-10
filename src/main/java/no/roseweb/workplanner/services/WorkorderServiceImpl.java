package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;
import no.roseweb.workplanner.repositories.WorkorderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkorderServiceImpl implements WorkorderService {

    private WorkorderRepository workorderRepository;

    public WorkorderServiceImpl(WorkorderRepository workorderRepository) {
        this.workorderRepository = workorderRepository;
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
    public List<Workorder> getAll(Integer limit, Integer offset) {
        return workorderRepository.getAll(limit, offset);
    }

    @Override
    public Integer countAll() {
        return workorderRepository.countAll();
    }
}
