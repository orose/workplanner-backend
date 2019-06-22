package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class RegistrationControllerTest extends BaseControllerTest {

    @Before
    public void init() {
        ApplicationUser user = new ApplicationUser();
        user.setEmail("email@example.com");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setOrganizationId(1L);
        user.setPassword("encryptedP4ssw0rd");

        when(userService.create(ArgumentMatchers.any())).thenReturn(user);
    }

    @Test
    public void allUsersShouldBeAbleToRegister() throws Exception {
        mvc.perform(post(RestPath.REGISTER)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"email@example.com\",\"firstname\":\"first\",\"lastname\":\"last\",\"password\":\"secret\"}")
        ).andExpect(status().isCreated())
            .andDo(
                document("register-post",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").description(""),
                                fieldWithPath("email").description(""),
                                fieldWithPath("firstname").description(""),
                                fieldWithPath("lastname").description(""),
                                fieldWithPath("organizationId").description(""),
                                fieldWithPath("password").description(""),
                                fieldWithPath("roles").description("")
                        )
                )
        );
    }
}
