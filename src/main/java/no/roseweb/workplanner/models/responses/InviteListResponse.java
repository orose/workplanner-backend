package no.roseweb.workplanner.models.responses;

import no.roseweb.workplanner.models.Invite;

import java.util.List;

public class InviteListResponse {
    private Integer offset;
    private Integer limit;
    private Integer total;
    private List<Invite> data;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Invite> getData() {
        return data;
    }

    public void setData(List<Invite> data) {
        this.data = data;
    }
}
