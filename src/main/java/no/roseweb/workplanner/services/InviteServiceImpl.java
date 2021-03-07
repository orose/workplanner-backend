package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.repositories.InviteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteServiceImpl implements InviteService {

    private InviteRepository inviteRepository;

    public InviteServiceImpl(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }

    @Override
    public Invite create(Invite invite) {
        return inviteRepository.create(invite);
    }

    @Override
    public Integer delete(String email) {
        return inviteRepository.delete(email);
    }

    @Override
    public Invite findByEmail(String email) {
        return inviteRepository.findByEmail(email);
    }

    @Override
    public List<Invite> findAllByOrganizationId(Long organizationId, Integer offset, Integer limit) {
        return inviteRepository.findAllByOrganizationId(organizationId, offset, limit);
    }

    @Override
    public Integer countAll(Long organizationId) {
        return inviteRepository.countAll(organizationId);
    }
}
