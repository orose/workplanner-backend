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

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class InviteControllerTest extends BaseControllerTest {

    @Test
    @WithMockUser
    public void createNewInvite() throws Exception {
        InviteRepositoryImpl inviteRepository = mock(InviteRepositoryImpl.class);
        when(inviteRepository.create(ArgumentMatchers.any())).thenReturn(new Invite());

        mvc.perform(post("/invite")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"email@example.com\",\"organization_id\":1}")
        ).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void getAllInvites() throws Exception {
        Invite i = new Invite();
        i.setOrganizationId(1L);
        i.setEmail("email@email.com");
        ArrayList<Invite> invites = new ArrayList<>();
        invites.add(i);

        InviteRepositoryImpl inviteRepository = mock(InviteRepositoryImpl.class);
        when(inviteRepository.findAllByOrganizationId(ArgumentMatchers.any())).thenReturn(invites);

        mvc.perform(get("/invite?organizationId=1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void deleteInvite() throws Exception {
        InviteRepositoryImpl inviteRepository = mock(InviteRepositoryImpl.class);
        when(inviteRepository.delete(ArgumentMatchers.any())).thenReturn(1);

        mvc.perform(delete("/invite?email=test@email.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
