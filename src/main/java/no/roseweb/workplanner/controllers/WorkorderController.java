package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.repositories.WorkorderRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class WorkorderController {
    private final WorkorderRepository workorderRepository;

    WorkorderController(WorkorderRepository workorderRepository) {
        this.workorderRepository = workorderRepository;
    }

    @RequestMapping(value = "/workorder", method = RequestMethod.POST)
    public Workorder createWorkorder(@RequestBody Workorder workorder, HttpServletResponse response) {

        Workorder createdWorkorder = workorderRepository.create(workorder);

        response.setStatus(HttpServletResponse.SC_CREATED);

        return createdWorkorder;
    }
}
