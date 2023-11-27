package com.cd.quizwhiz.server.auth;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.cd.quizwhiz.server.KeyManagement;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String auth = request.getHeader("Authorization");

        if (auth == null || auth.indexOf("Bearer ") == -1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String token = auth.split("Bearer ")[1];
        DecodedJWT decodedJWT;

        try {
            JWTVerifier verifier = JWT.require(KeyManagement.getAlgorithm())
                    // specify an specific claim validations
                    .withIssuer("quizwhiz")
                    // reusable verifier instance
                    .build();

            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception) {
            logger.error(exception);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        request.setAttribute("username", decodedJWT.getClaim("name").asString());
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        switch (request.getServletPath()) {
            case "/login":
            case "/register":
                return true;

            default:
                return false;
        }
    }

}
