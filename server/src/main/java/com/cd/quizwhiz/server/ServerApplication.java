package com.cd.quizwhiz.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.cd.quizwhiz.server.auth.Auth;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRegisterAttempt loginAttempt) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("message", null);
        ret.put("token", null);

        if (Auth.login(loginAttempt.username, loginAttempt.password)) {
            // Yay! All good!
            try {
                Map<String, String> payload = new HashMap<>();
                payload.put("name", loginAttempt.username);

                String token = JWT.create()
                        .withIssuer("quizwhiz")
                        .withPayload(payload)
                        .sign(KeyManagement.getAlgorithm());

                ret.put("token", token);

               return ResponseEntity.status(200).body(ret);

            } catch (JWTCreationException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ret);
            }
        }

        ret.put("message", "Invalid username or password.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ret);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody LoginRegisterAttempt loginAttempt) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("message", null);
        ret.put("token", null);

        String registrationMessage = Auth.register(loginAttempt.username, loginAttempt.password);
        if (registrationMessage.equals(loginAttempt.username)) {
            // Yay! All good!
            try {
                Map<String, String> payload = new HashMap<>();
                payload.put("name", loginAttempt.username);

                String token = JWT.create()
                        .withIssuer("quizwhiz")
                        .withPayload(payload)
                        .sign(KeyManagement.getAlgorithm());

                ret.put("token", token);

               return ResponseEntity.status(200).body(ret);

            } catch (JWTCreationException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ret);
            }
        }

        ret.put("message", registrationMessage);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ret);
    }

    @GetMapping("/username")
    public String username(HttpServletRequest request) {
        return (String) request.getAttribute("username");
    }
}
