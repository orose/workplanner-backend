package no.roseweb.workplanner.repositories;

public interface UserWorkorderRepository {
    Integer addAssignment(Long userId, Long workorderId);
    Integer removeAssignment(Long userId, Long workorderId);
    Integer removeAllAssignments(Long workorderId);
}
