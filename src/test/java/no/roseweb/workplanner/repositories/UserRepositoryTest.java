package no.roseweb.workplanner.repositories;

import static org.assertj.core.api.Assertions.assertThat;

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

import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.User;

@RunWith(SpringRunner.class)
@DataJdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void CreateAndReadWorks() {
        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate, bCryptPasswordEncoder);
        OrganizationRepository organizationRepository = new OrganizationRepositoryImpl(new NamedParameterJdbcTemplate(jdbcTemplate));
        Mockito.when(bCryptPasswordEncoder.encode(ArgumentMatchers.any())).thenReturn("password");

        Organization organization = new Organization();
        organization.setName("organization name");
        organization.setEmail("organization@email.com");
        organization.setOrganizationNumber("1234567890");
        organization = organizationRepository.create(organization);

        User user = new User();
        user.setEmail("test");
        user.setOrganizationId(organization.getId());
        userRepository.create(user);

        User u = userRepository.findByEmail("test");
        assertThat(u).isNotNull();
        assertThat(u.getOrganizationId()).isGreaterThanOrEqualTo(0L);
        assertThat(u.getId()).isGreaterThanOrEqualTo(0L);
    }
}
