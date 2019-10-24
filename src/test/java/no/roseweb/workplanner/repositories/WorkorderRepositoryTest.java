package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.Team;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class WorkorderRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void CrudFunctionality() {
        OrganizationRepository organizationRepository = new OrganizationRepositoryImpl(namedParameterJdbcTemplate);
        WorkorderRepository workorderRepository = new WorkorderRepositoryImpl(namedParameterJdbcTemplate);
        TeamRepository teamRepository = new TeamRepositoryImpl(namedParameterJdbcTemplate);
        ApplicationUser user = new ApplicationUser();
        user.setId(1L);
        user.setOrganizationId(2L);
        user.setEmail("test@example.com");

        Organization organization = new Organization();
        organization.setEmail("test@example.com");
        organization.setName("Organization");
        organization.setOrganizationNumber("1234");
        Organization createdOrganization = organizationRepository.create(organization);

        Team team = new Team();
        team.setName("Team");
        team.setOrganizationId(createdOrganization.getId());
        Team createdTeam = teamRepository.create(team);

        WorkorderCreateRequest wo = new WorkorderCreateRequest();
        wo.setTitle("Title");
        wo.setDescription("Description");
        Workorder createdWorkorder = workorderRepository.create(wo, user);
        assertThat(createdWorkorder.getId()).isNotNull();
        assertThat(createdWorkorder.getTeamId()).isNull();

        createdWorkorder.setDescription("Description updated");
        createdWorkorder.setTitle("Title updated");
        createdWorkorder.setTeamId(createdTeam.getId());
        Workorder updatedWorkorder = workorderRepository.update(createdWorkorder, user);
        assertThat(updatedWorkorder.getDescription()).isEqualTo("Description updated");
        assertThat(updatedWorkorder.getTitle()).isEqualTo("Title updated");
        assertThat(updatedWorkorder.getTeamId()).isNotNull();

        List<Workorder> workorderList = workorderRepository.getAll(10, 0);
        assertThat(workorderList.size()).isGreaterThan(0);
    }

}
