package no.roseweb.workplanner.authorization;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.repositories.OrganizationRepository;
import no.roseweb.workplanner.repositories.WorkorderRepository;
import no.roseweb.workplanner.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PermissionEvaluatorTest {

    private PermissionEvaluator evaluator;
    private static final String PERMISSION_EDIT = "edit";
    private static final String PERMISSION_READ = "read";

    @MockBean
    UserService userService;

    @MockBean
    WorkorderRepository workorderRepository;

    @MockBean
    OrganizationRepository organizationRepository;

    @Before
    public void init() {
        evaluator = new PermissionEvaluatorImpl(userService, workorderRepository, organizationRepository);
    }

    @Test
    public void testWorkorderPermissionDeniedForObjectNotBelongingOrganization () {
        String username = "test@user.com";
        long organizationId = 1L;
        long workorderId = 2L;

        ApplicationUser user = buildUser(username, organizationId);
        Workorder workorder = buildWorkorder(workorderId, organizationId + 2L);
        Authentication auth = buildAuthentication(username);

        when(userService.findByEmail(username)).thenReturn(user);
        when(workorderRepository.findById(workorderId)).thenReturn(workorder);

        Boolean result = evaluator.hasPermission(auth, workorder, PERMISSION_EDIT);
        assertThat(result).isEqualTo(false);

        Boolean result2 = evaluator.hasPermission(auth, workorder, PERMISSION_READ);
        assertThat(result2).isEqualTo(false);
    }

    @Test
    public void testWorkorderPermissionGrantedForObjectBelongingToOrganization () {
        String username = "test@user.com";
        long organizationId = 1L;
        long workorderId = 2L;

        ApplicationUser user = buildUser(username, organizationId);
        Workorder workorder = buildWorkorder(workorderId, organizationId);
        Authentication auth = buildAuthentication(username);

        when(userService.findByEmail(username)).thenReturn(user);
        when(workorderRepository.findById(workorderId)).thenReturn(workorder);

        Boolean result = evaluator.hasPermission(auth, workorder, PERMISSION_EDIT);
        assertThat(result).isEqualTo(true);

        Boolean result2 = evaluator.hasPermission(auth, workorder, PERMISSION_READ);
        assertThat(result2).isEqualTo(true);
    }

    @Test
    public void testOrganizationPermissionGrantedForObjectBelongingToOrganization () {
        String username = "test@user.com";
        long organizationId = 1L;

        ApplicationUser user = buildUser(username, organizationId);
        Authentication auth = buildAuthentication(username);
        Organization organization = buildOrganization(organizationId);

        when(userService.findByEmail(username)).thenReturn(user);

        Boolean result = evaluator.hasPermission(auth, organization, PERMISSION_EDIT);
        assertThat(result).isEqualTo(true);

        Boolean result2 = evaluator.hasPermission(auth, organization, PERMISSION_READ);
        assertThat(result2).isEqualTo(true);
    }

    @Test
    public void testOrganizationPermissionDeniedForObjectNotBelongingOrganization () {
        String username = "test@user.com";
        long organizationId = 1L;
        long workorderId = 2L;

        ApplicationUser user = buildUser(username, organizationId);
        Organization organization = buildOrganization(organizationId + 1L);
        Authentication auth = buildAuthentication(username);

        when(userService.findByEmail(username)).thenReturn(user);

        Boolean result = evaluator.hasPermission(auth, organization, PERMISSION_EDIT);
        assertThat(result).isEqualTo(false);

        Boolean result2 = evaluator.hasPermission(auth, organization, PERMISSION_READ);
        assertThat(result2).isEqualTo(false);
    }

    @Test
    public void testPermissionDeniedForUnknownPermission () {
        String username = "test@user.com";
        long organizationId = 1L;
        long workorderId = 2L;

        ApplicationUser user = buildUser(username, organizationId);
        Workorder workorder = buildWorkorder(workorderId, organizationId);
        Authentication auth = buildAuthentication(username);

        when(userService.findByEmail(username)).thenReturn(user);
        when(workorderRepository.findById(workorderId)).thenReturn(workorder);

        Boolean result = evaluator.hasPermission(auth, workorder, "unknownPermission");
        assertThat(result).isEqualTo(false);
    }

    private Authentication buildAuthentication(String name) {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return new User(name, "password", Collections.emptyList());
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    private ApplicationUser buildUser(String username, long organzationId) {
        ApplicationUser user = new ApplicationUser();
        user.setEmail(username);
        user.setOrganizationId(organzationId);
        return user;
    }

    private Workorder buildWorkorder(long workorderId, long organizationId) {
        Workorder workorder = new Workorder();
        workorder.setId(workorderId);
        workorder.setOrganizationId(organizationId);
        return workorder;
    }

    private Organization buildOrganization(long organizationId) {
        Organization organization = new Organization();
        organization.setId(organizationId);
        return organization;
    }
}
