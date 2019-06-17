package no.roseweb.workplanner.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.repositories.InviteRepository;

@RestController
public class InviteController {
    private final InviteRepository inviteRepository;

    InviteController(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }

    @PostMapping(value = "/invite")
    public Invite createInvite(@RequestBody Invite invite, HttpServletResponse response) {

        Invite createdInvite = inviteRepository.create(invite);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        return createdInvite;
    }

    @DeleteMapping(value = "/invite")
    public Integer deleteInvite(@RequestParam String email, HttpServletResponse response) {

        Integer affectedRows = inviteRepository.delete(email);

        if (affectedRows > 0) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    @GetMapping(value = "/invite")
    public List<Invite> findAllByOrganizationId(@RequestParam Long organizationId, HttpServletResponse response) {

        List<Invite> invites = inviteRepository.findAllByOrganizationId(organizationId);

        response.setStatus(HttpServletResponse.SC_OK);

        return invites;
    }
}
