package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Workorder;

import java.util.List;

public interface WorkorderRepository {
    Workorder create(Workorder workorder);
    Workorder update(Workorder workorder);
    Workorder findById(Long id);
    List<Workorder> getAll(Integer limit, Integer offset);
}
