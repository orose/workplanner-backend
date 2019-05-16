package no.roseweb.workplanner.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.repositories.OrganizationRepository;

@RestController
public class OrganizationController {
    private OrganizationRepository organizationRepository;

    OrganizationController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @GetMapping(value = "/organization/{id}")
    public Organization getOrganization(@PathVariable Long id, HttpServletResponse response) {
        Organization organization = organizationRepository.findById(id);

        response.setStatus(HttpServletResponse.SC_OK);

        return organization;
    }
}
