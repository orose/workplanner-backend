package no.roseweb.workplanner.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.repositories.InviteRepositoryImpl;
import no.roseweb.workplanner.repositories.OrganizationRepositoryImpl;
import no.roseweb.workplanner.repositories.TeamRepositoryImpl;
import no.roseweb.workplanner.repositories.UserRepository;
import no.roseweb.workplanner.repositories.UserRepositoryImpl;
import no.roseweb.workplanner.repositories.UserTeamRepositoryImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserServiceTest {

    @Autowired
    private OrganizationRepositoryImpl organizationRepository;

    @Autowired
    private InviteRepositoryImpl inviteRepository;

    @Autowired
    private TeamRepositoryImpl teamRepository;

    @Autowired
    private UserTeamRepositoryImpl userTeamRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createInvitedUser() {
        Mockito.when(bCryptPasswordEncoder.encode(ArgumentMatchers.any())).thenReturn("password");
        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate, bCryptPasswordEncoder);

        Organization o = new Organization();
        o.setOrganizationNumber("123");
        o.setEmail("organization@example.com");
        o.setName("Test");
        Organization organization = organizationRepository.create(o);
        Invite invite = new Invite("user@example.com", organization.getId());
        inviteRepository.create(invite);

        User inputUser = new User();
        inputUser.setEmail("user@example.com");
        inputUser.setFirstname("Firstname");
        inputUser.setLastname("Lastname");

        UserServiceImpl userService = new UserServiceImpl(inviteRepository, organizationRepository, teamRepository, userRepository, userTeamRepository);
        User createdUser = userService.create(inputUser);

        assertThat(createdUser.getOrganizationId()).isEqualTo(invite.getOrganizationId());
        assertThat(createdUser.getId()).isPositive();

        Invite deleted = inviteRepository.findByEmail(createdUser.getEmail());
        assertThat(deleted).isNull();
    }

    @Test
    public void createNonInvitedUser() {
        Mockito.when(bCryptPasswordEncoder.encode(ArgumentMatchers.any())).thenReturn("password");
        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate, bCryptPasswordEncoder);

        User inputUser = new User();
        inputUser.setEmail("another-user@example.com");
        inputUser.setFirstname("Firstname");
        inputUser.setLastname("Lastname");

        UserServiceImpl userService = new UserServiceImpl(inviteRepository, organizationRepository, teamRepository, userRepository, userTeamRepository);
        User createdUser = userService.create(inputUser);

        assertThat(createdUser.getOrganizationId()).isPositive();
        assertThat(createdUser.getId()).isPositive();
        assertThat(createdUser.getEmail()).isEqualTo(inputUser.getEmail());
    }
}
