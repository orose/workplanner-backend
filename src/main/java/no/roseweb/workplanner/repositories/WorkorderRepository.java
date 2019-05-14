package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Workorder;

public interface WorkorderRepository {
    Workorder create(Workorder workorder);
    Workorder update(Workorder workorder);
    Workorder findById(Long id);
}
