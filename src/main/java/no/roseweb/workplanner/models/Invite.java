package no.roseweb.workplanner.models;

public class Invite {
    private Long organizationId;
    private String email;

    public Invite() { }

    public Invite(String email, Long organizationId) {
       this.email = email;
       this.organizationId = organizationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
