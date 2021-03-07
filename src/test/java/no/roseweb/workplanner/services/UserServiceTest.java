package no.roseweb.workplanner.services;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.responses.UserListResponse;
import no.roseweb.workplanner.repositories.InviteRepository;
import no.roseweb.workplanner.repositories.TeamRepository;
import no.roseweb.workplanner.repositories.UserRepository;
import no.roseweb.workplanner.repositories.UserTeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    private UserService service;

    @MockBean
    private InviteRepository inviteRepository;

    @MockBean
    private OrganizationService organizationService;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserTeamRepository userTeamRepository;

    @Before
    public void init() {
        service = new UserServiceImpl(inviteRepository, organizationService, teamRepository, userRepository, userTeamRepository);
    }

    @Test
    public void findByOrganizationIdShouldReturnListOfUsers() {
        Organization o = new Organization();
        o.setId(1L);
        ApplicationUser user = new ApplicationUser();
        user.setId(10L);
        ApplicationUser user2 = new ApplicationUser();
        user.setId(20L);
        List<ApplicationUser> userList = Arrays.asList(user, user2);
        Integer offset = 0;
        Integer limit = 10;

        when(organizationService.findById(1L)).thenReturn(o);
        when(userRepository.findByOrganizationId(o.getId(), offset, limit)).thenReturn(userList);
        when(userRepository.countAllByOrganizationId(o.getId())).thenReturn(2);

        UserListResponse response = service.findByOrganizationId(o.getId(), offset, limit);

        assertThat(response.getData()).isEqualTo(userList);
        assertThat(response.getOffset()).isEqualTo(offset);
        assertThat(response.getLimit()).isEqualTo(limit);
        assertThat(response.getTotal()).isEqualTo(userList.size());
    }
}
