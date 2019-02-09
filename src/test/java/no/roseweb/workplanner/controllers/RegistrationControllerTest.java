package no.roseweb.workplanner.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void allUsersShouldHaveAccess() throws Exception {
        mvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\"oystein.rose@gmail.com\",\"firstname\":\"Øystein\",\"lastname\":\"Rose\",\"password\":\"secret\"}")
        )
        .andExpect(status().isOk());
    }
}
