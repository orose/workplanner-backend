package no.roseweb.workplanner.models;

public class Workorder {
    private Long id;
    private String title;
    private String description;
    private WorkorderStatus status;
    private Long teamId;
    private Long organizationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public WorkorderStatus getStatus() {
        return status;
    }

    public void setStatus(WorkorderStatus status) {
        this.status = status;
    }
}
