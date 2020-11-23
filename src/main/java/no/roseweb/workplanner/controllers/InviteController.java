package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.responses.InviteListResponse;
import no.roseweb.workplanner.services.InviteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class InviteController {
    private static final Logger LOG = LoggerFactory.getLogger(InviteController.class);
    private final InviteService inviteService;

    InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping(value = RestPath.INVITES)
    public ResponseEntity<Void> createInvite(@RequestBody Invite invite, HttpServletResponse response) {
        LOG.info("Create invite. Email={}, OrganizationId={}", invite.getEmail(), invite.getOrganizationId());

        Invite createdInvite = inviteService.create(invite);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdInvite.getEmail())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = RestPath.INVITES)
    public Integer deleteInvite(@RequestParam String email, HttpServletResponse response) {
        LOG.info("Delete invite. Email={}", email);

        Integer affectedRows = inviteService.delete(email);

        if (affectedRows > 0) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return null;
    }

    @GetMapping(value = RestPath.INVITES)
    public InviteListResponse findAllByOrganizationId(
        @RequestParam Long organizationId,
        @RequestParam(defaultValue = "10") Integer limit,
        @RequestParam(defaultValue = "0") Integer offset,
        HttpServletResponse response,
        Principal principal
    ) {
        LOG.info("Find all invites. OrganizationId={}, Offset={}, Limit={}", organizationId, offset, limit);

        List<Invite> invites = inviteService.findAllByOrganizationId(organizationId, offset, limit);

        InviteListResponse result = new InviteListResponse();
        result.setLimit(limit);
        result.setOffset(offset);
        result.setTotal(inviteService.countAll(organizationId));
        result.setData(invites);

        response.setStatus(HttpServletResponse.SC_OK);

        return result;
    }
}
