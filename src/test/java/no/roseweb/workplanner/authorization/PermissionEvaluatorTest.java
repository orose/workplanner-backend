package no.roseweb.workplanner.authorization;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.repositories.InviteRepository;
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
    private static final String WORKORDER = "Workorder";
    private static final String ORGANIZATION = "Organization";

    private static final String USERNAME = "test@user.com";

    @MockBean
    UserService userService;

    @MockBean
    WorkorderRepository workorderRepository;

    @MockBean
    OrganizationRepository organizationRepository;

    @MockBean
    InviteRepository inviteRepository;

    @Before
    public void init() {
        evaluator = new PermissionEvaluatorImpl(userService, workorderRepository, organizationRepository, inviteRepository);
    }

    @Test
    public void testWorkorderPermissionDeniedForObjectNotBelongingOrganization () {
        long organizationId = 1L;
        long workorderId = 2L;

        ApplicationUser user = buildUser(USERNAME, organizationId);
        Workorder workorder = buildWorkorder(workorderId, organizationId + 2L);
        Authentication auth = buildAuthentication(USERNAME);

        when(userService.findByEmail(USERNAME)).thenReturn(user);
        when(workorderRepository.findById(workorderId)).thenReturn(workorder);

        Boolean result = evaluator.hasPermission(auth, workorder, PERMISSION_EDIT);
        assertThat(result).isEqualTo(false);

        Boolean result2 = evaluator.hasPermission(auth, workorder, PERMISSION_READ);
        assertThat(result2).isEqualTo(false);

        Boolean result3 = evaluator.hasPermission(auth, workorderId, WORKORDER, PERMISSION_EDIT);
        assertThat(result3).isEqualTo(false);

        Boolean result4 = evaluator.hasPermission(auth, workorderId, WORKORDER, PERMISSION_READ);
        assertThat(result4).isEqualTo(false);
    }

    @Test
    public void testWorkorderPermissionGrantedForObjectBelongingToOrganization () {
        long organizationId = 1L;
        long workorderId = 2L;

        ApplicationUser user = buildUser(USERNAME, organizationId);
        Workorder workorder = buildWorkorder(workorderId, organizationId);
        Authentication auth = buildAuthentication(USERNAME);

        when(userService.findByEmail(USERNAME)).thenReturn(user);
        when(workorderRepository.findById(workorderId)).thenReturn(workorder);

        Boolean result = evaluator.hasPermission(auth, workorder, PERMISSION_EDIT);
        assertThat(result).isEqualTo(true);

        Boolean result2 = evaluator.hasPermission(auth, workorder, PERMISSION_READ);
        assertThat(result2).isEqualTo(true);

        Boolean result3 = evaluator.hasPermission(auth, workorderId, WORKORDER, PERMISSION_EDIT);
        assertThat(result3).isEqualTo(false);

        Boolean result4 = evaluator.hasPermission(auth, workorderId, WORKORDER, PERMISSION_READ);
        assertThat(result4).isEqualTo(true);
    }

    @Test
    public void testOrganizationPermissionGrantedForObjectBelongingToOrganization () {
        long organizationId = 1L;

        ApplicationUser user = buildUser(USERNAME, organizationId);
        Authentication auth = buildAuthentication(USERNAME);
        Organization organization = buildOrganization(organizationId);

        when(userService.findByEmail(USERNAME)).thenReturn(user);
        when(organizationRepository.findById(organizationId)).thenReturn(organization);

        Boolean result = evaluator.hasPermission(auth, organization, PERMISSION_EDIT);
        assertThat(result).isEqualTo(true);

        Boolean result2 = evaluator.hasPermission(auth, organization, PERMISSION_READ);
        assertThat(result2).isEqualTo(true);

        Boolean result3 = evaluator.hasPermission(auth, organizationId, ORGANIZATION, PERMISSION_EDIT);
        assertThat(result3).isEqualTo(false);

        Boolean result4 = evaluator.hasPermission(auth, organizationId, ORGANIZATION, PERMISSION_READ);
        assertThat(result4).isEqualTo(true);
    }

    @Test
    public void testOrganizationPermissionDeniedForObjectNotBelongingOrganization () {
        long organizationId = 1L;

        ApplicationUser user = buildUser(USERNAME, organizationId);
        Organization organization = buildOrganization(organizationId + 1L);
        Authentication auth = buildAuthentication(USERNAME);

        when(userService.findByEmail(USERNAME)).thenReturn(user);
        when(organizationRepository.findById(organizationId)).thenReturn(organization);

        Boolean result = evaluator.hasPermission(auth, organization, PERMISSION_EDIT);
        assertThat(result).isEqualTo(false);

        Boolean result2 = evaluator.hasPermission(auth, organization, PERMISSION_READ);
        assertThat(result2).isEqualTo(false);

        Boolean result3 = evaluator.hasPermission(auth, organizationId, ORGANIZATION, PERMISSION_EDIT);
        assertThat(result3).isEqualTo(false);

        Boolean result4 = evaluator.hasPermission(auth, organizationId, ORGANIZATION, PERMISSION_READ);
        assertThat(result4).isEqualTo(false);
    }

    @Test
    public void testPermissionDeniedForUnknownPermission () {
        long organizationId = 1L;
        long workorderId = 2L;

        ApplicationUser user = buildUser(USERNAME, organizationId);
        Workorder workorder = buildWorkorder(workorderId, organizationId);
        Authentication auth = buildAuthentication(USERNAME);

        when(userService.findByEmail(USERNAME)).thenReturn(user);
        when(workorderRepository.findById(workorderId)).thenReturn(workorder);

        Boolean result = evaluator.hasPermission(auth, workorder, "unknownPermission");
        Boolean result2 = evaluator.hasPermission(auth, workorderId, WORKORDER, "unknownPermission");

        assertThat(result).isEqualTo(false);
        assertThat(result2).isEqualTo(false);
    }

    @Test
    public void testPermissionDeniedForUnknownTargetType () {
        long workorderId = 2L;

        Authentication auth = buildAuthentication(USERNAME);

        Boolean result = evaluator.hasPermission(auth, workorderId, "unknownTargetType", PERMISSION_READ);

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
