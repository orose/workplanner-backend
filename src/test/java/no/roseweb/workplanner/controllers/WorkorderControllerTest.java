package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.Workorder;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class WorkorderControllerTest extends BaseControllerTest {

    @Before
    public void init() {
        Workorder workorder = new Workorder();
        workorder.setId(1L);
        workorder.setDescription("Description text");
        workorder.setTitle("workorder title");
        workorder.setTeamId(1L);

        when(workorderRepository.create(ArgumentMatchers.any())).thenReturn(workorder);
        when(workorderRepository.findById(ArgumentMatchers.anyLong())).thenReturn(workorder);
    }

    @Test
    @WithMockUser
    public void createNewWorkorder() throws Exception {
        mvc.perform(post(RestPath.WORKORDER)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"title\",\"description\":\"description\",\"teamId\":1}"))
        .andExpect(status().isCreated())
        .andDo(
            document("workorder-post",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("id").description(""),
                    fieldWithPath("description").description(""),
                    fieldWithPath("title").description(""),
                    fieldWithPath("teamId").description("")
                )
            )
        );
    }

    @Test
    @WithMockUser
    public void getWorkorder() throws Exception {
        mvc.perform(get(RestPath.WORKORDER_GET_ONE, "1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document("workorder-get",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("id").description(""),
                        fieldWithPath("description").description(""),
                        fieldWithPath("title").description(""),
                        fieldWithPath("teamId").description("")
                )
            )
        );
    }
}
