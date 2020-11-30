package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.Team;
import no.roseweb.workplanner.models.UserTeam;
import no.roseweb.workplanner.models.responses.UserListResponse;
import no.roseweb.workplanner.repositories.InviteRepository;
import no.roseweb.workplanner.repositories.TeamRepository;
import no.roseweb.workplanner.repositories.UserRepository;
import no.roseweb.workplanner.repositories.UserTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private InviteRepository inviteRepository;
    private OrganizationService organizationService;
    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private UserTeamRepository userTeamRepository;

    public UserServiceImpl(
        InviteRepository inviteRepository,
        OrganizationService organizationService,
        TeamRepository teamRepository,
        UserRepository userRepository,
        UserTeamRepository userTeamRepository
    ) {
        this.inviteRepository = inviteRepository;
        this.organizationService = organizationService;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.userTeamRepository = userTeamRepository;
    }

    @Override
    public ApplicationUser create(ApplicationUser user) {
        Invite invite = inviteRepository.findByEmail(user.getEmail());
        Team createdTeam = null;

        Organization organization;
        if (invite != null) {
            organization = organizationService.findById(invite.getOrganizationId());
        } else {
            organization = new Organization();
            organization.setEmail(user.getEmail());
            organization.setName(user.getFirstname() + " organization");
            organization = organizationService.create(organization, user);

            Team team = new Team();
            team.setName("Team A");
            team.setOrganizationId(organization.getId());
            createdTeam = teamRepository.create(team);
        }

        user.setOrganizationId(organization.getId());
        ApplicationUser createdUser = userRepository.create(user);

        if (createdTeam != null)  {
            connectToTeam(createdUser.getId(), createdTeam.getId(), "ADMIN");
        }

        if (invite != null) {
            inviteRepository.delete(invite.getEmail());
        }

        createdUser.setPassword(null);

        return createdUser;
    }

    @Override
    public int connectToTeam(Long userId, Long teamId, String permissionKey) {
        UserTeam ut = new UserTeam();
        ut.setTeamId(teamId);
        ut.setUserId(userId);
        ut.setPermissionKey(permissionKey);

        return userTeamRepository.create(ut);
    }

    @Override
    public void disconnectFromTeam(Long userId, Long teamId) {
        UserTeam ut = new UserTeam();
        ut.setTeamId(teamId);
        ut.setUserId(userId);

        userTeamRepository.remove(userId, teamId);
    }

    @Override
    public ApplicationUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserListResponse findByOrganizationId(Long organizationId, Integer offset, Integer limit) {
        Organization organization = organizationService.findById(organizationId);
        List<ApplicationUser> users = userRepository.findByOrganizationId(organization.getId(), offset, limit);
        Integer userCount = userRepository.countAllByOrganizationId(organization.getId());

        UserListResponse response = new UserListResponse();
        response.setData(users);
        response.setTotal(userCount);
        response.setLimit(limit);
        response.setOffset(offset);

        return response;
    }
}
