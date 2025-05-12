package com.springboot.blog.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


// Custom AuthenticationEntryPoint to handle unauthorized access and send error response
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // =================================================================================================================================

    // Method to handle unauthorized access and return a 401 error with the exception message
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Sending 401 Unauthorized status code and the exception message in the response
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
