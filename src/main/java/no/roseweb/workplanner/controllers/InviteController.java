package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.repositories.InviteRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
}
