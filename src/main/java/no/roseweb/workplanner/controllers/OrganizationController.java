package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.OrganizationUserListResponse;
import no.roseweb.workplanner.repositories.OrganizationRepository;
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
    private UserService userService;

    OrganizationController(OrganizationRepository organizationRepository, UserService userService) {
        this.organizationRepository = organizationRepository;
        this.userService = userService;
    }

    @GetMapping(value = RestPath.ORGANIZATION_ID)
    public Organization getOrganization(@PathVariable Long id, HttpServletResponse response) {
        LOG.info("Get organization info. Id={}", id);
        Organization organization = organizationRepository.findById(id);

        response.setStatus(HttpServletResponse.SC_OK);

        return organization;
    }

    @GetMapping(value = RestPath.ORGANIZATION_USER)
    public OrganizationUserListResponse getOrganizationUsers(
        @PathVariable Long id,
        @RequestParam(defaultValue = "0") Integer offset,
        @RequestParam(defaultValue = "10") Integer limit,
        HttpServletResponse servletResponse
    ) {
        LOG.info("Get users for organization with id={}", id);

        OrganizationUserListResponse response = userService.findByOrganizationId(id, offset, limit);

        servletResponse.setStatus(HttpServletResponse.SC_OK);

        return response;
    }
}
