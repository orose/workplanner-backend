package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.repositories.InviteRepository;
import no.roseweb.workplanner.repositories.OrganizationRepository;
import no.roseweb.workplanner.repositories.UserWorkorderRepository;
import no.roseweb.workplanner.repositories.WorkorderRepository;
import no.roseweb.workplanner.security.CustomAccessDeniedHandler;
import no.roseweb.workplanner.security.MySavedRequestAwareAuthenticationSuccessHandler;
import no.roseweb.workplanner.security.RestAuthenticationEntryPoint;
import no.roseweb.workplanner.services.InviteService;
import no.roseweb.workplanner.services.OrganizationService;
import no.roseweb.workplanner.services.UserService;
import no.roseweb.workplanner.services.WorkorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(outputDir = "target/snippets")
class BaseControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @MockBean
    MySavedRequestAwareAuthenticationSuccessHandler mySavedRequestAwareAuthenticationSuccessHandler;

    @MockBean
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @MockBean
    OrganizationRepository organizationRepository;

    @MockBean
    InviteRepository inviteRepository;

    @MockBean
    WorkorderRepository workorderRepository;

    @MockBean
    UserWorkorderRepository userWorkorderRepository;

    @MockBean
    UserService userService;

    @MockBean
    WorkorderService workorderService;

    @MockBean
    InviteService inviteService;

    @MockBean
    OrganizationService organizationService;
}
