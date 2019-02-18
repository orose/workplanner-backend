package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Team;
import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.models.UserTeam;
import no.roseweb.workplanner.services.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
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
public class UserTeamRepositoryTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void CrudFunctionality() {
        Mockito.when(bCryptPasswordEncoder.encode(ArgumentMatchers.any())).thenReturn("password");
        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate, bCryptPasswordEncoder);
        OrganizationRepository organizationRepository = new OrganizationRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));
        UserTeamRepository userTeamRepository = new UserTeamRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));
        InviteRepositoryImpl inviteRepository = new InviteRepositoryImpl(jdbcTemplate);
        TeamRepositoryImpl teamRepository = new TeamRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));

        User inputUser = new User();
        inputUser.setEmail("another-user@example.com");
        inputUser.setFirstname("Firstname");
        inputUser.setLastname("Lastname");

        UserServiceImpl userService = new UserServiceImpl(inviteRepository, organizationRepository, teamRepository, userRepository, userTeamRepository);
        User createdUser = userService.create(inputUser);

        Team t = new Team();
        t.setOrganizationId(createdUser.getOrganizationId());
        t.setName("team name");
        Team team = teamRepository.create(t);

        UserTeam ut = new UserTeam();
        ut.setUserEmail(createdUser.getEmail());
        ut.setPermissionKey("ADMIN");
        ut.setTeamId(team.getId());
        UserTeam userTeam = userTeamRepository.create(ut);

        assertThat(userTeam.getUserEmail()).isEqualTo(createdUser.getEmail());
        assertThat(userTeam.getId()).isPositive();

        Long id = userTeam.getId();
        UserTeam foundUserTeam = userTeamRepository.findById(id);
        assertThat(foundUserTeam.getId()).isEqualTo(id);

        userTeamRepository.remove(userTeam);
        UserTeam deletedUserTeam = userTeamRepository.findById(id);
        assertThat(deletedUserTeam).isNull();
    }

}
