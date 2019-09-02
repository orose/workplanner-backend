package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.WorkorderListResponse;
import no.roseweb.workplanner.repositories.WorkorderRepository;
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
    private final WorkorderRepository workorderRepository;

    WorkorderController(WorkorderRepository workorderRepository) {
        this.workorderRepository = workorderRepository;
    }

    @PostMapping(value = RestPath.API + RestPath.WORKORDER)
    public Workorder createWorkorder(@RequestBody Workorder workorder, HttpServletResponse response) {

        Workorder createdWorkorder = workorderRepository.create(workorder);

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
        result.setTotal(workorderRepository.countAll());
        result.setData(workorderRepository.getAll(limit, offset));

        response.setStatus(HttpServletResponse.SC_OK);

        return result;
    }

    @GetMapping(value = RestPath.API + RestPath.WORKORDER_ID)
    public Workorder getWorkorder(@PathVariable Long id, HttpServletResponse response) {

        Workorder workorder = workorderRepository.findById(id);

        response.setStatus(HttpServletResponse.SC_OK);

        return workorder;
    }

    @PutMapping(value = RestPath.API + RestPath.WORKORDER_ID)
    public Workorder updateWorkorder(
            @PathVariable Long id,
            @RequestBody Workorder workorder, HttpServletResponse response
    ) {

        if (workorderRepository.findById(id) == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        workorder.setId(id);
        Workorder updatedWorkorder = workorderRepository.update(workorder);

        response.setStatus(HttpServletResponse.SC_OK);

        return updatedWorkorder;
    }
}
