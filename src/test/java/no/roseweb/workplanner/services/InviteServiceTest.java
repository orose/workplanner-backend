package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.Invite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class InviteServiceTest {

    @Autowired
    private InviteService service;

    @Test
    public void shouldCallRepositoryCount() {
        Integer count = service.countAll(1L);
        assertThat(count).isPositive();
    }

    @Test
    public void shouldCallfindAll() {
        List<Invite> invites = service.findAllByOrganizationId(1L, 0, 10);
        assertThat(invites.size()).isPositive();
    }

    @Test
    @WithMockUser("test@email.com")
    public void shouldHaveAccess() {
        Invite invite = service.findByEmail("user@email.com");
        assertThat(invite).isNotNull();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser("invalid-user@email.com")
    public void shouldNotHaveAccessToRead() {
        service.findByEmail("test");
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser("invalid-user@email.com")
    public void shouldNotHaveAccessToDelete() {
        service.delete("test");
    }

    @Test
    @WithMockUser("test@email.com")
    public void shouldCallCreate() {
        Invite invite = new Invite();
        invite.setEmail("email@example.com");
        invite.setOrganizationId(1L);

        Invite created = service.create(invite);

        assertThat(created).isNotNull();
        assertThat(created.getEmail()).isEqualTo("email@example.com");
        assertThat(created.getOrganizationId()).isEqualTo(invite.getOrganizationId());
    }

    @Test
    @WithMockUser("test@email.com")
    public void shouldCallDelete() {
        Invite invite = new Invite();
        invite.setEmail("email2@example.com");
        invite.setOrganizationId(1L);

        Invite created = service.create(invite);

        assertThat(created).isNotNull();

        Integer affectedRows = service.delete(created.getEmail());
        assertThat(affectedRows).isEqualTo(1);

    }
}
