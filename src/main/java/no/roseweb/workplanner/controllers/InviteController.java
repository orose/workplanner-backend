package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.repositories.InviteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class InviteController {
    private static final Logger LOG = LoggerFactory.getLogger(InviteController.class);
    private final InviteRepository inviteRepository;

    InviteController(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }

    @PostMapping(value = RestPath.INVITE)
    public Invite createInvite(@RequestBody Invite invite, HttpServletResponse response) {
        LOG.info("Create invite. Email={}, OrganizationId={}", invite.getEmail(), invite.getOrganizationId());

        Invite createdInvite = inviteRepository.create(invite);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        return createdInvite;
    }

    @DeleteMapping(value = RestPath.INVITE)
    public Integer deleteInvite(@RequestParam String email, HttpServletResponse response) {
        LOG.info("Delete invite. Email={}", email);

        Integer affectedRows = inviteRepository.delete(email);

        if (affectedRows > 0) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return null;
    }

    @GetMapping(value = RestPath.INVITE)
    public List<Invite> findAllByOrganizationId(@RequestParam Long organizationId, HttpServletResponse response) {
        LOG.info("Find all invites. OrganizationId={}", organizationId);

        List<Invite> invites = inviteRepository.findAllByOrganizationId(organizationId);

        response.setStatus(HttpServletResponse.SC_OK);

        return invites;
    }
}
