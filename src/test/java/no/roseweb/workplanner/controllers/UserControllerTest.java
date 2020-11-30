package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.responses.UserListResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerTest extends BaseControllerTest {

    @Before
    public void init() {
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setEmail("company@email.com");
        organization.setName("Organization name");
        organization.setOrganizationNumber("1234");

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setEmail("test@email.com");
        applicationUser.setFirstname("Firstname");
        applicationUser.setLastname("Lastname");
        applicationUser.setOrganizationId(1L);
        applicationUser.setRoles(Collections.emptySet());
        applicationUser.setId(1L);

        UserListResponse response = new UserListResponse();
        response.setData(List.of(applicationUser));
        response.setLimit(10);
        response.setOffset(0);
        response.setTotal(1);

        when(userService.findByEmail(anyString())).thenReturn(applicationUser);
        when(userService.findByOrganizationId(anyLong(), anyInt(), anyInt())).thenReturn(response);
    }

    @Test
    @WithMockUser
    public void getShouldReturnUserList() throws Exception {
        mvc.perform(get(RestPath.API + RestPath.USERS)
            .param("offset", "0")
            .param("limit", "10")
            .param("organizationId", "1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andDo(
            document("userlist-get",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("offset").description(""),
                    fieldWithPath("limit").description(""),
                    fieldWithPath("total").description(""),
                    fieldWithPath("data[].id").description(""),
                    fieldWithPath("data[].email").description(""),
                    fieldWithPath("data[].password").description("Should always be null"),
                    fieldWithPath("data[].firstname").description(""),
                    fieldWithPath("data[].lastname").description(""),
                    fieldWithPath("data[].organizationId").description(""),
                    fieldWithPath("data[].roles[]").description("")
                )
            )
        );
    }

    @Test
    @WithMockUser
    public void getShouldReturnApplicationUser() throws Exception {
        mvc.perform(get(RestPath.API + RestPath.USERINFO)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("userinfo-get",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").description(""),
                                fieldWithPath("email").description(""),
                                fieldWithPath("password").description("Should always be null"),
                                fieldWithPath("firstname").description(""),
                                fieldWithPath("lastname").description(""),
                                fieldWithPath("organizationId").description(""),
                                fieldWithPath("roles[]").description("")
                        )
                )
        );
    }
}
