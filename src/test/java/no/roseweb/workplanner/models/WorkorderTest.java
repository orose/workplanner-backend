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

        assertEquals("Title", w.getTitle());
        assertEquals("Description", w.getDescription());
        assertEquals(new Long(1), w.getTeamId());
    }
}
