package no.roseweb.workplanner.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeamTest {

    @Test
    public void testGettersAndSetters() {
        Team t = new Team();

        t.setName("name");
        t.setOrganizationId(1L);
        t.setId(2L);

        assertEquals("name", t.getName());
        assertEquals(new Long(1), t.getOrganizationId());
        assertEquals(new Long(2), t.getId());
    }

    @Test
    public void testConstructor() {
        Team t = new Team("name", 1L);

        assertEquals("name", t.getName());
        assertEquals(new Long(1), t.getOrganizationId());
        assertEquals(null, t.getId());
    }
}
