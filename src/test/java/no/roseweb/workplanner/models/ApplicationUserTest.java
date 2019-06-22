package no.roseweb.workplanner.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationUserTest {

    @Test
    public void testGettersAndSetters() {
        ApplicationUser u = new ApplicationUser();

        u.setId(1L);
        u.setEmail("email@example.com");
        u.setPassword("password");
        u.setFirstname("firstname");
        u.setLastname("lastname");

        assertEquals("email@example.com", u.getEmail());
        assertEquals("password", u.getPassword());
        assertEquals("firstname", u.getFirstname());
        assertEquals("lastname", u.getLastname());
        assertEquals(new Long(1), u.getId());
    }
}
