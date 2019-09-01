package no.roseweb.workplanner.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AccessDeniedException ex
    ) throws IOException, ServletException {
        response.getOutputStream().print("Error Message Goes Here: " + ex.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // response.sendRedirect("/my-error-page");
    }
}