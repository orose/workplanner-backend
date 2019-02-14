package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.repositories.InviteRepositoryImpl;
import no.roseweb.workplanner.repositories.OrganizationRepositoryImpl;
import no.roseweb.workplanner.repositories.UserRepository;
import no.roseweb.workplanner.repositories.UserRepositoryImpl;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserServiceTest {

    @Autowired
    private OrganizationRepositoryImpl organizationRepository;

    @Autowired
    private InviteRepositoryImpl inviteRepository;

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
        Organization organization = organizationRepository.add(o);
        Invite invite = new Invite("user@example.com", organization.getId());
        inviteRepository.add(invite);

        User inputUser = new User();
        inputUser.setEmail("user@example.com");
        inputUser.setFirstname("Firstname");
        inputUser.setLastname("Lastname");

        UserServiceImpl userService = new UserServiceImpl(inviteRepository, organizationRepository, userRepository);
        User createdUser = userService.add(inputUser);

        assertThat(createdUser.getOrganizationId()).isEqualTo(invite.getOrganizationId());
    }

    @Test
    public void createNonInvitedUser() {
        Mockito.when(bCryptPasswordEncoder.encode(ArgumentMatchers.any())).thenReturn("password");
        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate, bCryptPasswordEncoder);

        User inputUser = new User();
        inputUser.setEmail("another-user@example.com");
        inputUser.setFirstname("Firstname");
        inputUser.setLastname("Lastname");

        UserServiceImpl userService = new UserServiceImpl(inviteRepository, organizationRepository, userRepository);
        User createdUser = userService.add(inputUser);

        assertThat(createdUser.getOrganizationId()).isPositive();
        assertThat(createdUser.getEmail()).isEqualTo(inputUser.getEmail());
    }
}
