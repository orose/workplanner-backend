package no.roseweb.workplanner.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InviteTest {

    @Test
    public void testGettersAndSetters() {
        Invite i = new Invite();

        i.setEmail("email@example.com");
        i.setOrganizationId(1L);

        assertEquals("email@example.com", i.getEmail());
        assertEquals(new Long(1), i.getOrganizationId());
    }
}
