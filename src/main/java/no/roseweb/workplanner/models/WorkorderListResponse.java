package no.roseweb.workplanner.models;

import java.util.List;

public class WorkorderListResponse {
    private Integer offset;
    private Integer limit;
    private Integer total;
    private List<Workorder> data;

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

    public List<Workorder> getData() {
        return data;
    }

    public void setData(List<Workorder> data) {
        this.data = data;
    }
}
