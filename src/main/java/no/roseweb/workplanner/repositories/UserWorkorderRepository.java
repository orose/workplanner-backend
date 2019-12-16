package no.roseweb.workplanner.repositories;

public interface UserWorkorderRepository {
    Integer addAssignment(String userId, Long workorderId);
    Integer removeAssignment(String userId, Long workorderId);
    Integer removeAllAssignments(Long workorderId);
}
