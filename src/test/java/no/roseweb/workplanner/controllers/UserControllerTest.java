package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
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
public class UserControllerTest extends BaseControllerTest {

    @Before
    public void init() {
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setEmail("company@email.com");
        organization.setName("Organization name");
        organization.setOrganizationNumber("1234");

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setPassword("secretPassw0rd");
        applicationUser.setEmail("test@email.com");
        applicationUser.setFirstname("Firstname");
        applicationUser.setLastname("Lastname");
        applicationUser.setOrganizationId(1L);
        applicationUser.setRoles(Collections.emptySet());
        applicationUser.setId(1L);

        when(userService.findByEmail(ArgumentMatchers.anyString())).thenReturn(applicationUser);
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
