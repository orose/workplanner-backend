package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.Organization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OrganizationServiceTest {

    @Autowired
    private OrganizationService service;

    @Test
    @WithMockUser("test@email.com")
    public void shouldHaveAccess() {
        Organization organization = service.findById(1L);
        assertThat(organization).isNotNull();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser("invalid-user@email.com")
    public void shouldNotHaveAccess() {
        service.findById(1L);
    }
}
