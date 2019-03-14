package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.repositories.OrganizationRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class OrganizationController {
    private OrganizationRepository organizationRepository;

    OrganizationController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @RequestMapping(value = "/organization/{id}", method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable Long id, HttpServletResponse response) {
        Organization organization = organizationRepository.findById(id);

        response.setStatus(HttpServletResponse.SC_OK);

        return organization;
    }
}
