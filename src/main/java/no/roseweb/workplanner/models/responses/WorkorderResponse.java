package no.roseweb.workplanner.models.responses;

import no.roseweb.workplanner.models.Workorder;

public class WorkorderResponse {
    private Workorder data;

    public Workorder getData() {
        return data;
    }

    public void setData(Workorder data) {
        this.data = data;
    }
}
