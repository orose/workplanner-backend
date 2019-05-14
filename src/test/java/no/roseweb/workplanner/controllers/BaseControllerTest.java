package no.roseweb.workplanner.controllers;

import no.roseweb.workplanner.auth.CustomAccessDeniedHandler;
import no.roseweb.workplanner.auth.MySavedRequestAwareAuthenticationSuccessHandler;
import no.roseweb.workplanner.auth.RestAuthenticationEntryPoint;
import no.roseweb.workplanner.repositories.InviteRepository;
import no.roseweb.workplanner.repositories.OrganizationRepository;
import no.roseweb.workplanner.repositories.WorkorderRepository;
import no.roseweb.workplanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

public class BaseControllerTest {
    @Autowired
    protected MockMvc mvc;

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
    UserService userService;
}
