package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.OrganizationUserListResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

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
public class OrganizationControllerTest extends BaseControllerTest {

    @Before
    public void init() {
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setEmail("company@email.com");
        organization.setName("Organization name");
        organization.setOrganizationNumber("1234");

        ApplicationUser user = new ApplicationUser();
        user.setId(2L);
        user.setOrganizationId(1L);
        user.setEmail("test@test.com");
        user.setFirstname("Firstname");
        user.setLastname("Lastname");

        OrganizationUserListResponse userlist = new OrganizationUserListResponse();
        userlist.setOffset(0);
        userlist.setLimit(10);
        userlist.setTotal(0);
        userlist.setData(Collections.singletonList(user));

        when(organizationService.findById(ArgumentMatchers.anyLong())).thenReturn(organization);
        when(userService.findByOrganizationId(
            ArgumentMatchers.anyLong(),
            ArgumentMatchers.eq(0),
            ArgumentMatchers.eq(10)))
            .thenReturn(userlist);
    }

    @Test
    @WithMockUser
    public void getShouldReturnOrganization() throws Exception {
        mvc.perform(get(RestPath.ORGANIZATIONS_ID,"1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("organization-get",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data.id").description(""),
                                fieldWithPath("data.email").description(""),
                                fieldWithPath("data.name").description(""),
                                fieldWithPath("data.organizationNumber").description("")
                        )
                )
        );
    }

    @Test
    @WithMockUser
    public void getShouldReturnOrganizationsUsers() throws Exception {
        mvc.perform(get(RestPath.ORGANIZATION_USER,"1")
            .param("offset", "0")
            .param("limit", "10")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("organizationUser-get",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("offset").description(""),
                        fieldWithPath("limit").description(""),
                        fieldWithPath("total").description(""),
                        fieldWithPath("data.[].id").description(""),
                        fieldWithPath("data.[].email").description(""),
                        fieldWithPath("data.[].firstname").description(""),
                        fieldWithPath("data.[].lastname").description(""),
                        fieldWithPath("data.[].organizationId").description(""),
                        fieldWithPath("data.[].roles").description(""),
                        fieldWithPath("data.[].password").description("")
                    )
                )
            );
    }
}
