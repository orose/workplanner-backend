package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.WorkorderListResponse;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;
import no.roseweb.workplanner.services.UserService;
import no.roseweb.workplanner.services.WorkorderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class WorkorderController {
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
        Workorder createdWorkorder = workorderService.create(request, this.getCurrentUser());

        response.setStatus(HttpServletResponse.SC_CREATED);

        return createdWorkorder;
    }

    @GetMapping(value = RestPath.API + RestPath.WORKORDER)
    public WorkorderListResponse getWorkorderList(
        @RequestParam(defaultValue = "10") Integer limit,
        @RequestParam(defaultValue = "0") Integer offset,
        HttpServletResponse response
    ) {
        WorkorderListResponse result = new WorkorderListResponse();
        result.setLimit(limit);
        result.setOffset(offset);
        result.setTotal(workorderService.countAll());
        result.setData(workorderService.getAll(limit, offset));

        response.setStatus(HttpServletResponse.SC_OK);

        return result;
    }

    @GetMapping(value = RestPath.API + RestPath.WORKORDER_ID)
    public Workorder getWorkorder(@PathVariable Long id, HttpServletResponse response) {

        Workorder workorder = workorderService.findById(id);

        response.setStatus(HttpServletResponse.SC_OK);

        return workorder;
    }

    @PutMapping(value = RestPath.API + RestPath.WORKORDER_ID)
    public Workorder updateWorkorder(
            @PathVariable Long id,
            @RequestBody Workorder body, HttpServletResponse response
    ) {
        body.setId(id);
        Workorder updatedWorkorder = workorderService.update(body, this.getCurrentUser());

        response.setStatus(HttpServletResponse.SC_OK);

        return updatedWorkorder;
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
