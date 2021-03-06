package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Team;
import no.roseweb.workplanner.models.UserTeam;
import no.roseweb.workplanner.services.OrganizationService;
import no.roseweb.workplanner.services.OrganizationServiceImpl;
import no.roseweb.workplanner.services.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ApplicationUserTeamRepositoryTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void CrudFunctionality() {
        Mockito.when(bCryptPasswordEncoder.encode(ArgumentMatchers.any())).thenReturn("password");
        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate, bCryptPasswordEncoder);
        OrganizationRepository organizationRepository = new OrganizationRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));
        UserTeamRepository userTeamRepository = new UserTeamRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));
        InviteRepositoryImpl inviteRepository = new InviteRepositoryImpl(namedParameterJdbcTemplate);
        TeamRepositoryImpl teamRepository = new TeamRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));

        OrganizationService organizationService = new OrganizationServiceImpl(organizationRepository);

        ApplicationUser inputUser = new ApplicationUser();
        inputUser.setEmail("another-user@example.com");
        inputUser.setFirstname("Firstname");
        inputUser.setLastname("Lastname");

        UserServiceImpl userService = new UserServiceImpl(inviteRepository, organizationService, teamRepository, userRepository, userTeamRepository);
        ApplicationUser createdUser = userService.create(inputUser);

        Team t = new Team();
        t.setOrganizationId(createdUser.getOrganizationId());
        t.setName("team name");
        Team team = teamRepository.create(t);

        UserTeam ut = new UserTeam();
        ut.setUserId(createdUser.getId());
        ut.setPermissionKey("ADMIN");
        ut.setTeamId(team.getId());
        int affectedRows = userTeamRepository.create(ut);

        assertThat(affectedRows).isEqualTo(1);

        affectedRows = userTeamRepository.remove(createdUser.getId(), team.getId());
        assertThat(affectedRows).isEqualTo(1);
    }

}
