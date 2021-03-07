package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.responses.UserListResponse;
import no.roseweb.workplanner.services.OrganizationService;
import no.roseweb.workplanner.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final OrganizationService organizationService;

    UserController(UserService userService, OrganizationService organizationService) {
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @GetMapping(value = RestPath.API + RestPath.USERINFO)
    public ApplicationUser getUserinfo(HttpServletResponse response, Principal principal) {
        LOG.info("Get userinfo. Username={}", principal.getName());

        ApplicationUser user = userService.findByEmail(principal.getName());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        user.setPassword(null);

        response.setStatus(HttpServletResponse.SC_OK);

        return user;
    }

    @GetMapping(value = RestPath.API + RestPath.USERS)
    public UserListResponse getUserList(
        @RequestParam(defaultValue = "10") Integer limit,
        @RequestParam(defaultValue = "0") Integer offset,
        @RequestParam(required = false) Long organizationId,
        HttpServletResponse response
    ) {
        LOG.info("Get users. organizationId={}", organizationId);
        validateParams(organizationId);

        UserListResponse result = userService.findByOrganizationId(organizationId, offset, limit);

        response.setStatus(HttpServletResponse.SC_OK);

        return result;
    }

    private void validateParams(Long organizationId) {
        if (organizationId == null) {
            throw new IllegalArgumentException("OrganizationId cannot be null");
        }
    }
}
