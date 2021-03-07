package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Invite;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        when(inviteService.create(any(Invite.class))).thenReturn(invite);
        when(inviteService.findAllByOrganizationId(anyLong(), anyInt(), anyInt())).thenReturn(inviteList);
        when(inviteService.delete("test@email.com")).thenReturn(1);
        when(inviteService.delete("not-found@email.com")).thenReturn(0);
    }

    @Test
    @WithMockUser
    public void createNewInvite() throws Exception {
        mvc.perform(post(RestPath.INVITES)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"email@example.com\",\"organizationId\":1}")
        ).andExpect(
            status().isCreated())
            .andDo(
                document("invite-post",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseHeaders(headerWithName(HttpHeaders.LOCATION).description("Url to the newly created object"))
                )
        );
    }

    @Test
    @WithMockUser
    public void getAllInvites() throws Exception {
        mvc.perform(get(RestPath.INVITES + "?organizationId=1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isOk())
            .andDo(
                document("invite-get",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("offset").description(""),
                        fieldWithPath("limit").description(""),
                        fieldWithPath("total").description(""),
                        fieldWithPath("data.[]").description("An array with invite objects"),
                        fieldWithPath("data.[].email").description(""),
                        fieldWithPath("data.[].organizationId").description("")
                    )
                )
            );
    }

    @Test
    @WithMockUser
    public void deleteInvite() throws Exception {
        mvc.perform(delete(RestPath.INVITES + "?email=test@email.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isNoContent())
            .andDo(document("invite-delete"));
    }

    @Test
    @WithMockUser
    public void deleteInviteNotFound() throws Exception {
        mvc.perform(delete(RestPath.INVITES + "?email=not-found@email.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
