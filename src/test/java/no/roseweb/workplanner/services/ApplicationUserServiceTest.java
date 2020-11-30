package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.Team;
import no.roseweb.workplanner.models.UserTeam;
import no.roseweb.workplanner.repositories.InviteRepositoryImpl;
import no.roseweb.workplanner.repositories.TeamRepositoryImpl;
import no.roseweb.workplanner.repositories.UserRepositoryImpl;
import no.roseweb.workplanner.repositories.UserTeamRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ApplicationUserServiceTest {

    @MockBean
    private OrganizationServiceImpl organizationService;

    @MockBean
    private InviteRepositoryImpl inviteRepository;

    @MockBean
    private UserRepositoryImpl userRepository;

    @MockBean
    private TeamRepositoryImpl teamRepository;

    @MockBean
    private UserTeamRepositoryImpl userTeamRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createInvitedUser() {
        Long organizationId = 1L;
        String inviteEmail = "user@example.com";

        Organization organization = new Organization();
        organization.setOrganizationNumber("123");
        organization.setEmail("organization@example.com");
        organization.setName("Test");
        organization.setId(organizationId);

        Invite invite = new Invite(inviteEmail, organization.getId());

        ApplicationUser user = new ApplicationUser();
        user.setEmail(inviteEmail);
        user.setFirstname("firstname");

        Mockito.when(bCryptPasswordEncoder.encode(any())).thenReturn("password");

        Mockito.when(inviteRepository.findByEmail(anyString())).thenReturn(invite);
        Mockito.when(organizationService.findById(organizationId)).thenReturn(organization);
        Mockito.when(userRepository.create(any())).thenReturn(user);

        UserServiceImpl userService = new UserServiceImpl(inviteRepository, organizationService, teamRepository, userRepository, userTeamRepository);

        userService.create(user);

        verify(userRepository, times(1)).create(any(ApplicationUser.class));
        verify(inviteRepository, times(1)).delete(anyString());
        verify(organizationService, never()).create(any(Organization.class), any(ApplicationUser.class));
        verify(teamRepository, never()).create(any(Team.class));
        verify(userTeamRepository, never()).create(any(UserTeam.class));
    }

    @Test
    public void createNonInvitedUser() {
        Long organizationId = 1L;
        Long teamId = 2L;
        String inviteEmail = "user@example.com";

        Organization organization = new Organization();
        organization.setOrganizationNumber("123");
        organization.setEmail("organization@example.com");
        organization.setName("Test");
        organization.setId(organizationId);

        ApplicationUser user = new ApplicationUser();
        user.setEmail(inviteEmail);
        user.setFirstname("firstname");

        Team team = new Team();
        team.setOrganizationId(organizationId);
        team.setName("team name");
        team.setId(teamId);

        Mockito.when(bCryptPasswordEncoder.encode(any())).thenReturn("password");

        Mockito.when(organizationService.create(any(), any())).thenReturn(organization);
        Mockito.when(userRepository.create(any())).thenReturn(user);
        Mockito.when(teamRepository.create(any())).thenReturn(team);

        UserServiceImpl userService = new UserServiceImpl(inviteRepository, organizationService, teamRepository, userRepository, userTeamRepository);

        userService.create(user);

        verify(userRepository, times(1)).create(any(ApplicationUser.class));
        verify(inviteRepository, never()).delete(anyString());
        verify(organizationService, times(1)).create(any(Organization.class), any(ApplicationUser.class));
        verify(teamRepository, times(1)).create(any(Team.class));
        verify(userTeamRepository, times(1)).create(any(UserTeam.class));
    }
}
