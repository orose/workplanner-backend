package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.repositories.InviteRepository;
import no.roseweb.workplanner.repositories.OrganizationRepository;
import no.roseweb.workplanner.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private InviteRepository inviteRepository;
    private OrganizationRepository organizationRepository;

    public UserServiceImpl(
        InviteRepository inviteRepository,
        OrganizationRepository organizationRepository,
        UserRepository userRepository
    ) {
        this.inviteRepository = inviteRepository;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        Invite invite = inviteRepository.findByEmail(user.getEmail());

        Organization organization;
        if (invite != null) {
            organization = organizationRepository.findById(invite.getOrganizationId());
        } else {
            organization = new Organization();
            organization.setEmail(user.getEmail());
            organization.setName(user.getFirstname() + " organization");
            organization = organizationRepository.add(organization);
        }

        user.setOrganizationId(organization.getId());
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        return userRepository.add(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
