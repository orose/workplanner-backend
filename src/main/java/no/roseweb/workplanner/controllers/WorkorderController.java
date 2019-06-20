package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.repositories.WorkorderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @GetMapping(value = RestPath.WORKORDER)
    public List<Workorder> getWorkorderList(
        @RequestParam Integer limit,
        @RequestParam Integer offset,
        HttpServletResponse response
    ) {

        List<Workorder> workorders = workorderRepository.getAll(limit, offset);

        response.setStatus(HttpServletResponse.SC_OK);

        return workorders;
    }

    @GetMapping(value = RestPath.WORKORDER_GET_ONE)
    public Workorder getWorkorder(@PathVariable Long id, HttpServletResponse response) {

        Workorder workorder = workorderRepository.findById(id);

        response.setStatus(HttpServletResponse.SC_OK);

        return workorder;
    }
}
