package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.responses.OrganizationResponse;
import no.roseweb.workplanner.models.responses.UserListResponse;
import no.roseweb.workplanner.repositories.OrganizationRepository;
import no.roseweb.workplanner.services.OrganizationService;
import no.roseweb.workplanner.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class OrganizationController {
    private static final Logger LOG = LoggerFactory.getLogger(OrganizationController.class);
    private OrganizationRepository organizationRepository;
    private OrganizationService organizationService;
    private UserService userService;

    OrganizationController(
        OrganizationRepository organizationRepository,
        UserService userService,
        OrganizationService organizationService
    ) {
        this.organizationRepository = organizationRepository;
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @GetMapping(value = RestPath.ORGANIZATIONS_ID)
    public OrganizationResponse getOrganization(@PathVariable Long id, HttpServletResponse response) {
        LOG.info("Get organization info. Id={}", id);
        Organization organization = organizationService.findById(id);

        response.setStatus(HttpServletResponse.SC_OK);

        OrganizationResponse organizationResponse = new OrganizationResponse();
        organizationResponse.setData(organization);

        return organizationResponse;
    }

    @GetMapping(value = RestPath.ORGANIZATION_USER)
    public UserListResponse getOrganizationUsers(
        @PathVariable Long id,
        @RequestParam(defaultValue = "0") Integer offset,
        @RequestParam(defaultValue = "10") Integer limit,
        HttpServletResponse servletResponse
    ) {
        LOG.info("Get users for organization with id={}", id);

        UserListResponse response = userService.findByOrganizationId(id, offset, limit);

        servletResponse.setStatus(HttpServletResponse.SC_OK);

        return response;
    }
}
