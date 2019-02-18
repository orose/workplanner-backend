package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.Team;
import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.models.UserTeam;
import no.roseweb.workplanner.repositories.InviteRepository;
import no.roseweb.workplanner.repositories.OrganizationRepository;
import no.roseweb.workplanner.repositories.TeamRepository;
import no.roseweb.workplanner.repositories.UserRepository;
import no.roseweb.workplanner.repositories.UserTeamRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private InviteRepository inviteRepository;
    private OrganizationRepository organizationRepository;
    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private UserTeamRepository userTeamRepository;

    public UserServiceImpl(
        InviteRepository inviteRepository,
        OrganizationRepository organizationRepository,
        TeamRepository teamRepository,
        UserRepository userRepository,
        UserTeamRepository userTeamRepository
    ) {
        this.inviteRepository = inviteRepository;
        this.organizationRepository = organizationRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.userTeamRepository = userTeamRepository;
    }

    @Override
    public User create(User user) {
        Invite invite = inviteRepository.findByEmail(user.getEmail());
        Team createdTeam = null;

        Organization organization;
        if (invite != null) {
            organization = organizationRepository.findById(invite.getOrganizationId());
        } else {
            organization = new Organization();
            organization.setEmail(user.getEmail());
            organization.setName(user.getFirstname() + " organization");
            organization = organizationRepository.create(organization);

            Team team = new Team();
            team.setName("Team A");
            team.setOrganizationId(organization.getId());
            createdTeam = teamRepository.create(team);
        }

        user.setOrganizationId(organization.getId());
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        User createdUser = userRepository.create(user);

        if (createdTeam != null)  {
            connectToTeam(createdUser.getEmail(), createdTeam.getId(), "ADMIN");
        }

        return createdUser;
    }

    @Override
    public UserTeam connectToTeam(String email, Long teamId, String permissionKey) {
        UserTeam ut = new UserTeam();
        ut.setTeamId(teamId);
        ut.setUserEmail(email);
        ut.setPermissionKey(permissionKey);

        return userTeamRepository.create(ut);
    }

    @Override
    public void disconnectFromTeam(String email, Long teamId) {
        UserTeam ut = new UserTeam();
        ut.setTeamId(teamId);
        ut.setUserEmail(email);

        userTeamRepository.remove(ut);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
