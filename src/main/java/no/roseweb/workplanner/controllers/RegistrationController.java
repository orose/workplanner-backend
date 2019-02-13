package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.repository.InviteRepository;
import no.roseweb.workplanner.repository.OrganizationRepository;
import no.roseweb.workplanner.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/register")
public class RegistrationController {
    private UserService userService;
    private InviteRepository inviteRepository;
    private OrganizationRepository organizationRepository;

    RegistrationController(
        UserService userService,
        InviteRepository inviteRepository,
        OrganizationRepository organizationRepository
    ) {
        this.userService = userService;
        this.inviteRepository = inviteRepository;
        this.organizationRepository = organizationRepository;
    }

    @PostMapping(value = "")
    public User registerUser(@RequestBody User user, HttpServletResponse response) {
        Invite invite = inviteRepository.findByEmail(user.getEmail());
        Organization organization;

        if (invite != null) {
            organization = organizationRepository.findById(invite.getOrganizationId());
        } else {
            organization = new Organization();
            organization.setEmail(user.getEmail());
            organization.setName(user.getFirstname() + " organization");
            organization = organizationRepository.add(organization);
        }

        user.setOrganizationId(organization.getId());
        userService.add(user);

        response.setStatus(HttpServletResponse.SC_CREATED);

        return user;
    }
}
