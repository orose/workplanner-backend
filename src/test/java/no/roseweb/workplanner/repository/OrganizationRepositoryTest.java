package no.roseweb.workplanner.repository;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OrganizationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void InsertAndReadWorks() {
        OrganizationRepository organizationRepository = new OrganizationRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));

        Organization organization = new Organization();
        organization.setName("organization name");
        organization.setEmail("organization@email.com");
        organization.setOrganizationNumber("1234567890");
        organization = organizationRepository.add(organization);

        Organization existing = organizationRepository.findById(1L);
        assertThat(existing).isNotNull();

        Organization nonExisting = organizationRepository.findById(99999L);
        assertThat(nonExisting).isNull();

        List<Organization> organizations = organizationRepository.findAllOrderedByName();
        assertThat(organizations.size()).isGreaterThan(0);
    }
}
