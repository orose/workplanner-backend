package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.repositories.InviteRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class InviteController {
    private final InviteRepository inviteRepository;

    InviteController(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }

    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public Invite createInvite(@RequestBody Invite invite, HttpServletResponse response) {

        Invite createdInvite = inviteRepository.create(invite);

        response.setStatus(HttpServletResponse.SC_CREATED);

        return createdInvite;
    }

    @RequestMapping(value = "/invite", method = RequestMethod.DELETE)
    public Integer deleteInvite(@RequestParam String email, HttpServletResponse response) {

        Integer affectedRows = inviteRepository.delete(email);

        response.setStatus(HttpServletResponse.SC_OK);

        return affectedRows;
    }

    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public List<Invite> findAllByOrganizationId(@RequestParam Long organizationId, HttpServletResponse response) {

        List<Invite> invites = inviteRepository.findAllByOrganizationId(organizationId);

        response.setStatus(HttpServletResponse.SC_OK);

        return invites;
    }
}
