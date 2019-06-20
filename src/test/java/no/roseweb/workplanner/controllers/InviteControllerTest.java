package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Invite;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class InviteControllerTest extends BaseControllerTest {

    @Before
    public void init() {
        Invite invite = new Invite("email@email.com", 1L);

        ArrayList<Invite> inviteList = new ArrayList<>();
        inviteList.add(invite);

        when(inviteRepository.create(ArgumentMatchers.any(Invite.class))).thenReturn(invite);
        when(inviteRepository.findAllByOrganizationId(ArgumentMatchers.any())).thenReturn(inviteList);
        when(inviteRepository.delete(ArgumentMatchers.any())).thenReturn(1);
    }

    @Test
    @WithMockUser
    public void createNewInvite() throws Exception {
        mvc.perform(post(RestPath.INVITE)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"email@example.com\",\"organizationId\":1}")
        ).andExpect(
            status().isCreated())
            .andDo(
                document("invite-post",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("email").description(""),
                        fieldWithPath("organizationId").description("")
                    )
                )
            );
    }

    @Test
    @WithMockUser
    public void getAllInvites() throws Exception {
        mvc.perform(get(RestPath.INVITE + "?organizationId=1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isOk())
            .andDo(
                document("invite-get",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("[]").description("An array with invite objects"),
                        fieldWithPath("[].email").description(""),
                        fieldWithPath("[].organizationId").description("")
                    )
                )
            );
    }

    @Test
    @WithMockUser
    public void deleteInvite() throws Exception {
        mvc.perform(delete(RestPath.INVITE + "?email=test@email.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isNoContent())
            .andDo(document("invite-delete"));
    }
}
