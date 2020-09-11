package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.WorkorderListResponse;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;
import no.roseweb.workplanner.services.UserService;
import no.roseweb.workplanner.services.WorkorderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class WorkorderController {
    private static final Logger LOG = LoggerFactory.getLogger(WorkorderController.class);
    private final WorkorderService workorderService;
    private final UserService userService;

    WorkorderController(
        WorkorderService workorderService,
        UserService userService
    ) {
        this.workorderService = workorderService;
        this.userService = userService;
    }

    @PostMapping(value = RestPath.API + RestPath.WORKORDER)
    public Workorder createWorkorder(@RequestBody WorkorderCreateRequest request, HttpServletResponse response) {
        LOG.info("Create workorder. Title={}", request.getTitle());
        Workorder createdWorkorder = workorderService.create(request, this.getCurrentUser());

        response.setStatus(HttpServletResponse.SC_CREATED);

        return createdWorkorder;
    }

    @GetMapping(value = RestPath.API + RestPath.WORKORDER)
    public WorkorderListResponse getWorkorderList(
        @RequestParam(defaultValue = "10") Integer limit,
        @RequestParam(defaultValue = "0") Integer offset,
        HttpServletResponse response,
        Principal principal
    ) {
        ApplicationUser user = userService.findByEmail(principal.getName());
        Long organizationId = user.getOrganizationId();

        LOG.info("Get list. OrganizationId={}, Offset={}, Limit={}", organizationId, offset, limit);
        WorkorderListResponse result = new WorkorderListResponse();
        result.setLimit(limit);
        result.setOffset(offset);
        result.setTotal(workorderService.countAll(organizationId));
        result.setData(workorderService.getAll(organizationId, limit, offset));

        response.setStatus(HttpServletResponse.SC_OK);

        return result;
    }

    @GetMapping(value = RestPath.API + RestPath.WORKORDER_ID)
    public Workorder getWorkorder(@PathVariable Long id, HttpServletResponse response) {
        LOG.info("Get workorder. Id={}", id);

        Workorder workorder = workorderService.findById(id);

        response.setStatus(HttpServletResponse.SC_OK);

        return workorder;
    }

    @PutMapping(value = RestPath.API + RestPath.WORKORDER_ID)
    public Workorder updateWorkorder(
            @PathVariable Long id,
            @RequestBody Workorder body, HttpServletResponse response
    ) {
        LOG.info("Update workorder. Id={}", id);
        body.setId(id);
        Workorder updatedWorkorder = workorderService.update(body, this.getCurrentUser());

        response.setStatus(HttpServletResponse.SC_OK);

        return updatedWorkorder;
    }

    @PostMapping(value = RestPath.API + RestPath.WORKORDER_ASSIGN)
    public Workorder assignWorkorder(
            @PathVariable Long id,
            @PathVariable Long userId,
            HttpServletResponse response
    ) {
        LOG.info("Assign workorder. Id={}, UserId={}", id, userId);
        Workorder w = workorderService.findById(id);
        Integer affectedRows = workorderService.assignUser(w, userId);

        if (affectedRows == 1) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        }

        return w;
    }

    @DeleteMapping(value = RestPath.API + RestPath.WORKORDER_ASSIGN)
    public Workorder unassignWorkorder(
            @PathVariable Long id,
            @PathVariable Long userId,
            HttpServletResponse response
    ) {
        LOG.info("Unassign workorder. Id={}, UserId={}", id, userId);
        Workorder w = workorderService.findById(id);
        Integer affectedRows = workorderService.unassignUser(w, userId);

        if (affectedRows == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        return w;
    }

    private ApplicationUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            return userService.findByEmail((String) auth.getPrincipal());
        } catch (ClassCastException e) {
            User u = (User) auth.getPrincipal();
            return userService.findByEmail(u.getUsername());
        }
    }
}
