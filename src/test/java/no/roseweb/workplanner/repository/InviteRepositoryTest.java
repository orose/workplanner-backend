package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.Organization;
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
public class InviteRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void UserRepositoryAndOrganizationTest() {
        OrganizationRepository organizationRepository = new OrganizationRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));

        Organization organization = new Organization();
        organization.setName("organization name");
        organization.setEmail("organization@email.com");
        organization.setOrganizationNumber("1234567890");
        organizationRepository.add(organization);

        InviteRepository inviteRepository = new InviteRepositoryImpl(jdbcTemplate);
        Invite invite = new Invite();
        invite.setEmail("test@email.com");
        invite.setOrganizationId(1L);

        inviteRepository.add(invite);
        Invite fetchedInvite = inviteRepository.findByEmail("test@email.com");
        assertThat(fetchedInvite).isNotNull();

        inviteRepository.delete(fetchedInvite);
        assertThat(inviteRepository.findByEmail("test@email.com")).isNull();
    }

}
