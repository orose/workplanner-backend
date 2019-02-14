package no.roseweb.workplanner.models;

public class Team {
    private Long id;
    private Long organizationId;
    private String name;

    public Team() { }

    public Team(String name, Long organizationId) {
       this.name = name;
       this.organizationId = organizationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
