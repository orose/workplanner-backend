package no.roseweb.workplanner.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.repositories.WorkorderRepository;

@RestController
public class WorkorderController {
    private final WorkorderRepository workorderRepository;

    WorkorderController(WorkorderRepository workorderRepository) {
        this.workorderRepository = workorderRepository;
    }

    @PostMapping(value = RestPath.WORKORDER)
    public Workorder createWorkorder(@RequestBody Workorder workorder, HttpServletResponse response) {

        Workorder createdWorkorder = workorderRepository.create(workorder);

        response.setStatus(HttpServletResponse.SC_CREATED);

        return createdWorkorder;
    }
}
