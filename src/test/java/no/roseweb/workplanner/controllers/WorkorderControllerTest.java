package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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

        List<Workorder> workorderList = new ArrayList<>();
        workorderList.add(workorder);

        ApplicationUser user = new ApplicationUser();
        user.setEmail("test@example.com");
        user.setOrganizationId(2L);
        user.setId(1L);
        user.setLastname("Lastname");
        user.setFirstname("Firstname");

        when(workorderService.create(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(workorder);
        when(workorderService.findById(1L)).thenReturn(workorder);
        when(workorderService
            .getAll(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any()))
            .thenReturn(workorderList);
        when(workorderService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(workorder);
        when(workorderService.countAll(ArgumentMatchers.any())).thenReturn(123);
        when(userService.findByEmail(ArgumentMatchers.anyString())).thenReturn(user);

        when(workorderService.assignUser(ArgumentMatchers.any(Workorder.class), ArgumentMatchers.anyLong())).thenReturn(1);
        when(workorderService.unassignUser(ArgumentMatchers.any(Workorder.class), ArgumentMatchers.anyLong())).thenReturn(1);
    }

    @Test
    @WithMockUser
    public void createNewWorkorder() throws Exception {
        mvc.perform(post(RestPath.API + RestPath.WORKORDERS)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"title\":\"title\",\"description\":\"description\",\"teamId\":1}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andDo(
            document("workorder-post",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseHeaders(headerWithName(HttpHeaders.LOCATION).description("Url to the newly created object"))
            )
        );
    }

    @Test
    @WithMockUser
    public void getWorkorder() throws Exception {
        mvc.perform(get(RestPath.API + RestPath.WORKORDERS_ID, "1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document("workorder-get",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("data.id").description(""),
                    fieldWithPath("data.description").description(""),
                    fieldWithPath("data.title").description(""),
                    fieldWithPath("data.teamId").description(""),
                    fieldWithPath("data.status").description(""),
                    fieldWithPath("data.organizationId").description("")
                )
            )
        );
    }

    @Test
    @WithMockUser
    public void updateWorkorder() throws Exception {
        mvc.perform(put(RestPath.API + RestPath.WORKORDERS_ID, "1")
        .content("{\"title\":\"title\",\"description\":\"description\",\"teamId\":1,\"organizationId\":1}")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document("workorder-update",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("data.id").description(""),
                    fieldWithPath("data.description").description(""),
                    fieldWithPath("data.title").description(""),
                    fieldWithPath("data.teamId").description(""),
                    fieldWithPath("data.status").description(""),
                    fieldWithPath("data.organizationId").description("")
                )
            )
        );
    }

    @Test
    @WithMockUser
    public void updateWorkorderNotFound() throws Exception {
        mvc.perform(put(RestPath.WORKORDERS_ID, "2")
        .content("{\"title\":\"title\",\"description\":\"description\",\"teamId\":1}")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void workorderList() throws Exception {
        mvc.perform(get(RestPath.API + RestPath.WORKORDERS)
        .param("offset", "0")
        .param("limit", "1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document("workorder-getAll",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("offset").description(""),
                    parameterWithName("limit").description("")
                ),
                responseFields(
                    fieldWithPath("total").description(""),
                    fieldWithPath("offset").description(""),
                    fieldWithPath("limit").description(""),
                    fieldWithPath("data.[].id").description(""),
                    fieldWithPath("data.[].description").description(""),
                    fieldWithPath("data.[].title").description(""),
                    fieldWithPath("data.[].teamId").description(""),
                    fieldWithPath("data.[].status").description(""),
                    fieldWithPath("data.[].organizationId").description("")
                )
            )
        );
    }

    @Test
    @WithMockUser
    public void assignWorkorder() throws Exception {
        mvc.perform(post(RestPath.API + RestPath.WORKORDERS_ASSIGN, "1", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(
                        document("workorder-assign",
                                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("data.id").description(""),
                                        fieldWithPath("data.description").description(""),
                                        fieldWithPath("data.title").description(""),
                                        fieldWithPath("data.teamId").description(""),
                                        fieldWithPath("data.status").description(""),
                                        fieldWithPath("data.organizationId").description("")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    public void unassignWorkorder() throws Exception {
        mvc.perform(delete(RestPath.API + RestPath.WORKORDERS_ASSIGN, "1", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("workorder-unassign",
                                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("data.id").description(""),
                                        fieldWithPath("data.description").description(""),
                                        fieldWithPath("data.title").description(""),
                                        fieldWithPath("data.teamId").description(""),
                                        fieldWithPath("data.status").description(""),
                                        fieldWithPath("data.organizationId").description("")
                                )
                        )
                );
    }
}
