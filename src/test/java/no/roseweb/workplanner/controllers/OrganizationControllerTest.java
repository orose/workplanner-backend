package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Organization;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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

        when(organizationRepository.findById(ArgumentMatchers.anyLong())).thenReturn(organization);
    }

    @Test
    @WithMockUser
    public void getShouldReturnOrganization() throws Exception {
        mvc.perform(get(RestPath.ORGANIZATION_GET_ONE,"1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("organization-get",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").description(""),
                                fieldWithPath("email").description(""),
                                fieldWithPath("name").description(""),
                                fieldWithPath("organizationNumber").description("")
                        )
                )
        );
    }
}
