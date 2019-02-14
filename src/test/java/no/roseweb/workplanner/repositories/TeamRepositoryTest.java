package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TeamRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void TeamAndOrganizationTest() {
        Organization o = new Organization();
        o.setName("organization name");
        o.setEmail("organization@email.com");
        o.setOrganizationNumber("1234567890");

        OrganizationRepository organizationRepository = new OrganizationRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));
        Organization organization = organizationRepository.add(o);

        Team t = new Team("name", organization.getId());

        TeamRepository teamRepository = new TeamRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));
        Team team = teamRepository.create(t);

        Team fetchedTeam = teamRepository.findById(team.getId());
        assertThat(fetchedTeam).isNotNull();

        teamRepository.delete(fetchedTeam);
        assertThat(teamRepository.findById(team.getId())).isNull();
    }

}
