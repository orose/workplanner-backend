package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.repositories.OrganizationRepositoryImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class OrganizationControllerTest extends BaseControllerTest {

    @Test
    @WithMockUser
    public void getShouldReturnOrganization() throws Exception {
        OrganizationRepositoryImpl organizationRepository = mock(OrganizationRepositoryImpl.class);
        when(organizationRepository.findById(ArgumentMatchers.anyLong())).thenReturn(new Organization());

        mvc.perform(get("/organization/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
