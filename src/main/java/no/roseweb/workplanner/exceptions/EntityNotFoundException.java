package no.roseweb.workplanner.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String simpleName, Long id) {
        super(String.format("Entity not found. Class=%s, id=%d", simpleName, id));
    }
}
