package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.WorkorderStatus;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
public class WorkorderServiceTest {

    @Autowired
    private WorkorderService service;

    @Test
    public void shouldCallRepositoryCount() {
        Integer count = service.countAll(ArgumentMatchers.any());
        assertThat(count).isPositive();
    }

    @Test
    public void shouldCallgetAll() {
        List<Workorder> workorders = service.getAll(10L, 10, 0);
        assertThat(workorders.size()).isPositive();
    }

    @Test
    @WithMockUser("test@email.com")
    public void shouldHaveAccess() {
        Workorder workorder = service.findById(1L);
        assertThat(workorder).isNotNull();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser("invalid-user@email.com")
    public void shouldNotHaveAccess() {
        service.findById(1L);
    }

    @Test
    @WithMockUser("test@email.com")
    public void shouldCallCreate() {
        WorkorderCreateRequest request = new WorkorderCreateRequest();
        request.setDescription("created description");
        request.setTitle("created title");
        ApplicationUser user = new ApplicationUser();
        user.setOrganizationId(1L);

        Workorder workorder = service.create(request, user);

        assertThat(workorder).isNotNull();
        assertThat(workorder.getDescription()).isEqualTo("created description");
        assertThat(workorder.getTitle()).isEqualTo("created title");
        assertThat(workorder.getId()).isPositive();
        assertThat(workorder.getOrganizationId()).isEqualTo(user.getOrganizationId());
    }

    @Test
    @WithMockUser("test@email.com")
    public void shouldCallUpdate() {
        Workorder workorder = new Workorder();
        workorder.setId(1L);
        workorder.setDescription("description updated");
        workorder.setTitle("title updated");
        workorder.setStatus(WorkorderStatus.IN_PROGRESS);
        ApplicationUser user = new ApplicationUser();
        user.setOrganizationId(1L);

        Workorder updated = service.update(workorder, user);

        assertThat(updated).isNotNull();
        assertThat(updated.getTitle()).isEqualTo("title updated");
        assertThat(updated.getDescription()).isEqualTo("description updated");
    }

    @Test
    @WithMockUser("test@email.com")
    public void shouldCallAssignUser() {
        Workorder workorder = new Workorder();
        workorder.setId(1L);
        workorder.setDescription("description updated");
        workorder.setTitle("title updated");
        workorder.setStatus(WorkorderStatus.IN_PROGRESS);
        ApplicationUser user = new ApplicationUser();
        user.setOrganizationId(1L);

        Integer rows = service.assignUser(workorder, "test@email.com");

        assertThat(rows).isEqualTo(1L);
    }

    @Test
    @WithMockUser("test@email.com")
    public void shouldCallUnassignUser() {
        Workorder workorder = new Workorder();
        workorder.setId(1L);
        workorder.setDescription("description updated");
        workorder.setTitle("title updated");
        workorder.setStatus(WorkorderStatus.IN_PROGRESS);
        ApplicationUser user = new ApplicationUser();
        user.setOrganizationId(1L);

        service.assignUser(workorder, "test@email.com");
        Integer rows = service.unassignUser(workorder, "test@email.com");

        assertThat(rows).isEqualTo(1L);
    }
}
