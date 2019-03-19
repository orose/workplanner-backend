package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.repositories.InviteRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class InvitationControllerTest extends BaseControllerTest {

    @Test
    @WithMockUser
    public void getShouldReturnOrganization() throws Exception {
        InviteRepositoryImpl inviteRepository = mock(InviteRepositoryImpl.class);
        when(inviteRepository.create(ArgumentMatchers.any())).thenReturn(new Invite());

        mvc.perform(post("/invite")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"email@example.com\",\"organization_id\":1}")
        ).andExpect(status().isCreated());
    }
}
