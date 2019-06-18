package no.roseweb.workplanner.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class RegistrationControllerTest extends BaseControllerTest {

    @Test
    public void allUsersShouldBeAbleToRegister() throws Exception {
        mvc.perform(post(RestPath.REGISTER)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"email@example.com\",\"firstname\":\"first\",\"lastname\":\"last\",\"password\":\"secret\"}")
        ).andExpect(status().isCreated());
    }
}
