package no.roseweb.workplanner.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorkorderTest {

    @Test
    public void testGettersAndSetters() {
        Workorder w = new Workorder();

        w.setTitle("Title");
        w.setDescription("Description");
        w.setTeamId(1L);
        w.setStatus(WorkorderStatus.NEW);

        assertEquals("Title", w.getTitle());
        assertEquals("Description", w.getDescription());
        assertEquals(WorkorderStatus.NEW, w.getStatus());
        assertEquals(new Long(1), w.getTeamId());
    }
}
